package com.store.api.store.backend.controllers

import com.store.api.store.backend.models.Category
import com.store.api.store.backend.services.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    // GET /api/categories
    @GetMapping
    fun getCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Int): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(id)
        return if (category.isPresent) {
            ResponseEntity.ok(category.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // POST /api/categories
    @PostMapping
    fun addCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val savedCategory = categoryService.addCategory(category)
        return ResponseEntity.ok(savedCategory)
    }

    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Int, @RequestBody category: Category): ResponseEntity<Category> {
        val updatedCategory = categoryService.updateCategory(id, category)
        return if (updatedCategory.isPresent) {
            ResponseEntity.ok(updatedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Int): ResponseEntity<Category> {
        val deletedCategory = categoryService.deleteCategory(id)
        return if (deletedCategory.isPresent) {
            ResponseEntity.ok(deletedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

}