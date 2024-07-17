package com.store.api.store.backend.repositories

import com.store.api.store.backend.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {

}
