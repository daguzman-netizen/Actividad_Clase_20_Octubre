package com.example.demo_prueba_1.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

enum class OrderStatus { PENDIENTE, ENVIADO, RECIBIDO }

@Entity
@Table(name = "pedidos")
open class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(name = "nombre_cliente", nullable = false)
    open var customerName: String = ""

    @Column(name = "productos_json", nullable = false, columnDefinition = "TEXT")
    open var productsJson: String = "[]"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var status: OrderStatus = OrderStatus.PENDIENTE

    @Column(name = "codigo_verificacion", nullable = false)
    open var verificationCode: String = ""
}
