package com.example.demo_prueba_1.controller

import com.example.demo_prueba_1.model.Product
import com.example.demo_prueba_1.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class ProductController {

    @Autowired
    open lateinit var productService: ProductService

    @GetMapping("/hola")
    open fun saludo(): String {
        return "Hola mundo"
    }

    @GetMapping("/adios")
    open fun despedida(): String {
        return "Adios mundo"
    }

    @GetMapping("/saludoService")
    open fun saludoService(): String {
        return this.productService.saludoService()
    }

    @GetMapping("/productos")
    open fun listar(): List<Product> {
        return productService.listarProductos()
    }

    @GetMapping("/productos/{id}")
    open fun buscar(@PathVariable id: Int): Product = productService.buscarProducto(id)

    @PostMapping("/productos")
    open fun crear(@RequestBody product: Product): ResponseEntity<Product> =
        ResponseEntity.status(HttpStatus.CREATED).body(productService.crearProducto(product))

    @PutMapping("/productos/{id}")
    open fun actualizar(@PathVariable id: Int, @RequestBody product: Product): Product =
        productService.actualizarProducto(id, product)
}
