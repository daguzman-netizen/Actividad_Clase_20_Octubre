package com.example.demo_prueba_1.repository

import com.example.demo_prueba_1.model.Order
import com.example.demo_prueba_1.model.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun existsByCustomerNameIgnoreCaseAndStatus(customerName: String, status: OrderStatus): Boolean
}
