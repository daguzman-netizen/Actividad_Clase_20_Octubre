package com.example.demo_prueba_1.model

data class OrderItemRequest(val productId: Int?, val quantity: Int?)
data class CreateOrderRequest(val customerName: String?, val products: List<OrderItemRequest>?)
data class VerificationRequest(val verificationCode: String?)
data class OrderItemResponse(val productId: Int, val productName: String, val quantity: Int)
data class OrderResponse(
    val id: Long?,
    val customerName: String,
    val products: List<OrderItemResponse>,
    val status: OrderStatus,
    val verificationCode: String?
)
