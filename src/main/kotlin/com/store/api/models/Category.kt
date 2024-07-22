package com.store.api.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "categories")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(name = "categoryname")
    @Size(min = 3, max = 50)
    @NotBlank(message = "Category name is required")
    var categoryName: String = "",

    @Column(name = "categorystatus")
    var categoryStatus: Int = 0

)