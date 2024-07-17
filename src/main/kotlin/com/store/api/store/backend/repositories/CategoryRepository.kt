package com.store.api.store.backend.repositories

import com.store.api.store.backend.models.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Int> {

}