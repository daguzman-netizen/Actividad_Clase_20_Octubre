package com.example.demo_prueba_1.service

import com.example.demo_prueba_1.model.Product
import com.example.demo_prueba_1.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
open class ProductService {

    @Autowired
    open lateinit var productRepository: ProductRepository

    open fun listarProductos(): List<Product> {
        return productRepository.findAll()
    }

    open fun buscarProducto(id: Int): Product = productRepository.findById(id)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Producto $id no encontrado") }

    open fun crearProducto(product: Product): Product {
        validar(product)
        val id = product.id ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo id es obligatorio")
        if (productRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con id $id")
        }
        return productRepository.save(product)
    }

    open fun actualizarProducto(id: Int, product: Product): Product {
        if (!productRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Producto $id no encontrado")
        }
        if (product.id != null && product.id != id) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cuerpo debe coincidir con el de la URL")
        }
        validar(product)
        product.id = id
        return productRepository.save(product)
    }

    private fun validar(product: Product) {
        if (product.name.isNullOrBlank() || product.description.isNullOrBlank() || product.stock == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "name, description y stock son obligatorios")
        }
        if (product.stock!! < 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "stock no puede ser negativo")
        }
    }

    open fun saludoService(): String {
        return "Hola desde el Service"
    }
}
