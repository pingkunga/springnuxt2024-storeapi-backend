package com.store.api.service

import com.store.api.dto.ProductDTO
import com.store.api.model.Product
import com.store.api.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

    //-------------------------------------------------------------------------------------------------
    //File Upload
    @Throws(IOException::class)
    fun saveFile(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename

        //check if directory exists
        createDirectoryIfNotExists(Paths.get(uploadDir).normalize())

        val filePath = Paths.get(uploadDir, fileName)

        if (!Files.exists(filePath.parent)) {
            Files.createDirectories(filePath.parent)
        }

        FileOutputStream(filePath.toFile()).use { fos ->
            fos.write(file.bytes)
        }

        return fileName
    }

    private fun createDirectoryIfNotExists(path: Path) {
        if (Files.notExists(path)) {
            Files.createDirectories(path)
        }
    }

    fun deleteFile(fileName: String) {
        if (fileName != "noimg.jpg") {
            val filePath = Paths.get(uploadDir, fileName)
            try {
                Files.deleteIfExists(filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    //-------------------------------------------------------------------------------------------------
    // Get all products
    // select * from products
    // with search + pagination
    fun getAllProducts(searchQuery: String?, selectedCategory: Int?, page: Pageable): Page<ProductDTO> {
        return productRepository.findBySearchQueryAndCategory(searchQuery, selectedCategory, page)
    }

    fun getProductWithCategory(id: Int): Optional<ProductDTO> {
        return productRepository.findProductWithCategory(id)
    }

    // Get product by id
    // select * from products where id = ?
    fun getProductById(id: Int): Optional<Product> = productRepository.findById(id)

    // Create product
    // insert into products (product_name, product_price, product_quantity, product_image) values (?, ?, ?, ?)
    fun createProduct(product: Product, image: MultipartFile?): Product {
        if (image != null) {
            product.productPicture = saveFile(image)
        } else {
            product.productPicture = "noimg.jpg"
        }
        return productRepository.save(product)
    }

    // Update Product
    // update products set product_name = ?, product_price = ?, product_quantity = ?, product_image = ? where id = ?
    fun updateProduct(id: Int, updateProduct: Product, image: MultipartFile?): Product {
        return if (productRepository.existsById(id)) {
            val existingProduct = productRepository.findById(id).get()

            existingProduct.productName = updateProduct.productName
            existingProduct.unitPrice = updateProduct.unitPrice
            existingProduct.unitInStock = updateProduct.unitInStock
            existingProduct.categoryId = updateProduct.categoryId
            existingProduct.modifiedDate = updateProduct.modifiedDate

            if (image != null) {
                val newFileName = saveFile(image)
                deleteFile(existingProduct.productPicture!!)
                existingProduct.productPicture = newFileName
            }

            productRepository.save(existingProduct)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

    // Delete Product
    // delete from products where id = ?
    fun deleteProduct(id: Int) {
        if (productRepository.existsById(id)) {
            val product = productRepository.findById(id).get()
            deleteFile(product.productPicture!!)
            productRepository.delete(product)
        } else {
            throw RuntimeException("Product not found with id: $id")
        }
    }

}