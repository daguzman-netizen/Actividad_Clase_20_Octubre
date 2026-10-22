package com.example.demo_prueba_1.service

import org.springframework.stereotype.Service

@Service
class ProductService {

    fun saludoService(): String {
        return "Hola desde el Service"
    }
}