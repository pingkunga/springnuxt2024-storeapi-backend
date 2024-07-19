package com.store.api.store.backend.services

import com.store.api.store.backend.models.Product
import com.store.api.store.backend.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

    // Get all products
    // select * from products
    // with search + pagination
    fun getAllProducts(searchQuery: String?, selectedCategory: Int?, page: Pageable): Page<Product> {
        return productRepository.findBySearchQueryAndCategory(searchQuery, selectedCategory, page)
    }

    fun getProductWithCategory(id: Int): Optional<Map<String, Any>> {
        return productRepository.findProductWithCategory(id)
    }

    // Get product by id
    // select * from products where id = ?
    fun getProductById(id: Int): Optional<Product> = productRepository.findById(id)

    // Create product
    // insert into products (product_name, product_price, product_quantity, product_image) values (?, ?, ?, ?)
    fun createProduct(product: Product): Product = productRepository.save(product)

    // Update Product
    // update products set product_name = ?, product_price = ?, product_quantity = ?, product_image = ? where id = ?
    fun updateProduct(id: Int, updateProduct: Product): Product {
        return if (productRepository.existsById(id)) {
            updateProduct.id = id
            productRepository.save(updateProduct)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

    // Delete Product
    // delete from products where id = ?
    fun deleteProduct(id: Int) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

}