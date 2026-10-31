package com.example.demo_prueba_1.service

import com.example.demo_prueba_1.model.*
import com.example.demo_prueba_1.repository.OrderRepository
import com.example.demo_prueba_1.repository.ProductRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.SecureRandom

@Service
open class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val objectMapper: ObjectMapper
) {
    private val random = SecureRandom()

    open fun list(): List<OrderResponse> = orderRepository.findAll().map(::toResponse)

    open fun get(id: Long): OrderResponse = toResponse(findOrder(id))

    @Transactional
    open fun create(request: CreateOrderRequest): OrderResponse {
        val customer = request.customerName?.trim()
        if (customer.isNullOrEmpty()) badRequest("customerName es obligatorio")
        val items = request.products
        if (items.isNullOrEmpty()) badRequest("Debe incluir al menos un producto")
        if (orderRepository.existsByCustomerNameIgnoreCaseAndStatus(customer, OrderStatus.PENDIENTE)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya tiene un pedido pendiente")
        }

        val combined = items.groupBy { it.productId ?: badRequest("productId es obligatorio") }
            .map { (productId, lines) ->
                val quantity = lines.sumOf { it.quantity ?: 0 }
                if (quantity <= 0) badRequest("La cantidad debe ser mayor que cero")
                val product = productRepository.findById(productId).orElseThrow {
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Producto $productId no existe")
                }
                if (product.stock == null || product.stock!! < quantity) {
                    throw ResponseStatusException(HttpStatus.CONFLICT, "Stock insuficiente para ${product.name ?: "producto $productId"}")
                }
                product.stock = product.stock!! - quantity
                productRepository.save(product)
                OrderItemResponse(productId, product.name ?: "Producto $productId", quantity)
            }

        val order = Order().apply {
            customerName = customer
            productsJson = objectMapper.writeValueAsString(combined)
            status = OrderStatus.PENDIENTE
            verificationCode = (100000 + random.nextInt(900000)).toString()
        }
        return toResponse(orderRepository.save(order), includeCode = true)
    }

    @Transactional
    open fun ship(id: Long): OrderResponse {
        val order = findOrder(id)
        if (order.status != OrderStatus.PENDIENTE) badRequest("Solo se puede enviar un pedido pendiente")
        order.status = OrderStatus.ENVIADO
        return toResponse(orderRepository.save(order))
    }

    @Transactional
    open fun receive(id: Long, request: VerificationRequest): OrderResponse {
        val order = findOrder(id)
        if (order.status != OrderStatus.ENVIADO) badRequest("El pedido debe estar enviado antes de recibirlo")
        if (orderRepository.existsByCustomerNameIgnoreCaseAndStatus(order.customerName, OrderStatus.PENDIENTE)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "El cliente tiene otro pedido pendiente")
        }
        if (request.verificationCode.isNullOrBlank() || request.verificationCode != order.verificationCode) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de verificación incorrecto")
        }
        order.status = OrderStatus.RECIBIDO
        return toResponse(orderRepository.save(order))
    }

    private fun findOrder(id: Long) = orderRepository.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido $id no encontrado")
    }

    private fun toResponse(order: Order, includeCode: Boolean = false): OrderResponse {
        val lines: List<OrderItemResponse> = objectMapper.readValue(
            order.productsJson, object : TypeReference<List<OrderItemResponse>>() {}
        )
        return OrderResponse(order.id, order.customerName, lines, order.status,
            if (includeCode) order.verificationCode else null)
    }

    private fun badRequest(message: String): Nothing =
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, message)
}
