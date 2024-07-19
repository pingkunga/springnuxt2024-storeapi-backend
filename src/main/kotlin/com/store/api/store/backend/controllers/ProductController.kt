package com.store.api.store.backend.controllers

import com.store.api.store.backend.models.Product
import com.store.api.store.backend.services.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.time.LocalDateTime

@Tag(name = "Products", description = "APIs for managing products")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {
    // Get all products + search + pagination
    @Operation(summary = "Get all products", description = "Get all products from database")
    @GetMapping
    fun getAllProducts(
        @RequestParam(required = false, value = "searchQuery") searchQuery: String?,
        @RequestParam(required = false, value = "selectedCategory") selectedCategory: Int?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): ResponseEntity<Map<String,Any>> {
        val pageable:Pageable = PageRequest.of(page - 1, size)
        val products = productService.getAllProducts(searchQuery, selectedCategory, pageable)

        val response = mapOf(
            "data" to products.content,
            "currentPage" to products.number + 1,
            "totalItems" to products.totalElements,
            "totalPages" to products.totalPages
        )
        return ResponseEntity.ok(response)
    }

    // Get product with category
    @Operation(summary = "Get product with category", description = "Get product with category from database")
    @GetMapping("/{id}/category")
    fun getProductWithCategory(@PathVariable id: Int): ResponseEntity<Map<String, Any>> {
        val product = productService.getProductWithCategory(id)
        return product.map { ResponseEntity.ok(it) }
                      .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    // Get product by id
    @Operation(summary = "Get product by ID", description = "Get product by ID from database")
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Int): ResponseEntity<Product> {
        val  product = productService.getProductById(id)
        return product.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    // Create product
    // POST /api/products
    @Operation(summary = "Create product" , description = "Create product to database")
    @PostMapping(consumes = ["multipart/form-data"])
    fun createProduct(
        @RequestParam productName: String,
        @RequestParam unitPrice: BigDecimal,
        @RequestParam unitInStock: Int,
        @RequestParam(required = false) productPicture: String?,
        @RequestParam categoryId: Int,
        @RequestParam(required = false) createdDate: LocalDateTime?,
        @RequestParam(required = false) modifiedDate: LocalDateTime?,
        @RequestParam(required = false) image: MultipartFile?
    ): ResponseEntity<Product> {
        val product = Product(
            productName = productName,
            unitPrice = unitPrice,
            unitInStock = unitInStock,
            productPicture = productPicture,
            categoryId = categoryId,
            createdDate = createdDate ?: LocalDateTime.now(),
            modifiedDate = modifiedDate
        )
        val createdProduct = productService.createProduct(product, image)
        return ResponseEntity.status(201).body(createdProduct)
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