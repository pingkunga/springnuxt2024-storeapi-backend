package com.store.api.dtos

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductDTO(
    val id: Int,
    val productName: String?,
    val unitPrice: BigDecimal?,
    val unitInStock: Int?,
    val productPicture: String?,
    val categoryId: Int,
    val categoryName: String?,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime?
)
