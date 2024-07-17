package com.store.api.store.backend.controllers

import com.store.api.store.backend.models.Product
import com.store.api.store.backend.services.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Products", description = "APIs for managing products")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {
    // Get all products
    @Operation(summary = "Get all products", description = "Get all products from database")
    @GetMapping
    fun getAllProducts() = productService.getAllProducts()

    // Get product by id
    @Operation(summary = "Get product by ID", description = "Get product by ID from database")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Int): ResponseEntity<Product> {
        val  product = productService.getProductById(id)
        return product.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    // Create product
    @Operation(summary = "Create new product", description = "Create new product in database")
    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    // Update Product
    @Operation(summary = "Update product by ID", description = "Update product by ID from database")
    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Int, @RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(id, product)
        return ResponseEntity.ok(updatedProduct)
    }

    // Delete Product
    @Operation(summary = "Delete product by ID", description = "Delete product by ID from database")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Int): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}