package com.example.demo_prueba_1.controller

import com.example.demo_prueba_1.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping("/hola")
    fun saludo(): String {
        return "Hola mundo"
    }

    @GetMapping("/adios")
    fun despedida(): String {
        return "Adios mundo"
    }

    @GetMapping("/saludoService")
    fun saludoService(): String {
        return this.productService.saludoService()
    }
}