package com.store.api.controller

import com.store.api.dto.ProductDTO
import com.store.api.model.Product
import com.store.api.service.ProductService
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
        @RequestParam(defaultValue = "100") limit: Int
    ): ResponseEntity<Map<String,Any>> {
        val pageable:Pageable = PageRequest.of(page - 1, limit)
        val products = productService.getAllProducts(searchQuery, selectedCategory, pageable)

        val response = mapOf(
            "products" to products.content,
            "currentPage" to products.number + 1,
            "totalItems" to products.totalElements,
            "totalPages" to products.totalPages
        )
        return ResponseEntity.ok(response)
    }

    // Get product with category
    @Operation(summary = "Get product with category", description = "Get product with category from database")
    @GetMapping("/{id}/category")
    fun getProductWithCategory(@PathVariable id: Int): ResponseEntity<ProductDTO> {
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
    // PUT /api/products/{id}
    @Operation(summary = "Update product" , description = "Update product to database")
    @PutMapping("/{id}", consumes = ["multipart/form-data"])
    fun updateProduct(
        @PathVariable id: Int,
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
        val updatedProduct = productService.updateProduct(id, product, image)
        return ResponseEntity.ok(updatedProduct)
    }


    // Delete Product
    @Operation(summary = "Delete product" , description = "Delete product from database")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Int): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}