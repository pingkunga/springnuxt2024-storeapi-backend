package com.store.api.repositories

import com.store.api.dtos.ProductDTO
import com.store.api.models.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ProductRepository : JpaRepository<Product, Int> {

    /*
    @Query("SELECT p FROM Product p WHERE " +
            "(:searchQuery IS NULL OR p.productName LIKE %:searchQuery%) AND " +
            "(:selectedCategory IS NULL OR p.categoryId = :selectedCategory)")
    fun findBySearchQueryAndCategory(
        @Param("searchQuery") searchQuery: String?,
        @Param("selectedCategory") selectedCategory: Int?,
        pageable: Pageable
    ): Page<Product>
    */
    @Query("""
        SELECT new com.store.api.dtos.ProductDTO(p.id, p.productName, p.unitPrice, p.unitInStock, p.productPicture, c.id, c.categoryName, p.createdDate, p.modifiedDate)
        FROM Product p JOIN Category c ON p.categoryId = c.id
        WHERE (:searchQuery IS NULL OR :searchQuery = '' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
          AND (:selectedCategory IS NULL OR p.categoryId = :selectedCategory)
    """)
    fun findBySearchQueryAndCategory(
        @Param("searchQuery") searchQuery: String?,
        @Param("selectedCategory") selectedCategory: Int?,
        pageable: Pageable
    ): Page<ProductDTO>

    /*
    @Query("SELECT p FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.id = :id")
    fun findProductWithCategory(@Param("id") id: Int): Optional<Map<String, Any>>
    */

    @Query("""
        SELECT new com.store.api.dtos.ProductDTO(p.id, p.productName, p.unitPrice, p.unitInStock, p.productPicture, c.id, c.categoryName, p.createdDate, p.modifiedDate)
        FROM Product p JOIN Category c ON p.categoryId = c.id
        WHERE p.id = :id
    """)
    fun findProductWithCategory(@Param("id") id: Int): Optional<ProductDTO>


}
