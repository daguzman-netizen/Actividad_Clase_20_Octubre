package com.example.demo_prueba_1.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "producto")
open class Product {
    @Id
    open var id: Int? = null

    @Column(name = "nombre")
    open var name: String? = null

    @Column(name = "descripcion")
    open var description: String? = null
    open var price: String? = null
    open var stock: Int? = null
}
