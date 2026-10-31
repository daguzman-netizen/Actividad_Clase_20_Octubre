package com.example.demo_prueba_1.controller

import com.example.demo_prueba_1.model.CreateOrderRequest
import com.example.demo_prueba_1.model.OrderResponse
import com.example.demo_prueba_1.model.VerificationRequest
import com.example.demo_prueba_1.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedidos")
open class OrderController(private val orderService: OrderService) {
    @GetMapping
    open fun list(): List<OrderResponse> = orderService.list()

    @GetMapping("/{id}")
    open fun get(@PathVariable id: Long): OrderResponse = orderService.get(id)

    @PostMapping
    open fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<OrderResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request))

    @PostMapping("/{id}/enviar")
    open fun ship(@PathVariable id: Long): OrderResponse = orderService.ship(id)

    @PostMapping("/{id}/recibir")
    open fun receive(@PathVariable id: Long, @RequestBody request: VerificationRequest): OrderResponse =
        orderService.receive(id, request)
}
