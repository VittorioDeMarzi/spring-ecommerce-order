package ecommerce.service

import ecommerce.dto.ProductPatchRequest
import ecommerce.dto.ProductRequest
import ecommerce.dto.ProductResponse
import ecommerce.dto.toEntity
import ecommerce.exception.ProductAlreadyInDBException
import ecommerce.exception.ProductCreationException
import ecommerce.exception.ProductNotFoundException
import ecommerce.exception.ProductUpdateException
import ecommerce.model.toDto
import ecommerce.repository.ProductRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun findById(id: Long): ProductResponse {
        val product =
            productRepository.findById(id).getOrNull() ?: throw ProductNotFoundException("Product not found, id: $id")
        return product.toDto()
    }

    fun findAll(): List<ProductResponse> {
        val products = productRepository.findAll()
        return products.map { it.toDto() }
    }

    fun createProduct(productRequest: ProductRequest): ProductResponse {
        if (productRepository.existsByName(productRequest.name)) {
            throw ProductAlreadyInDBException("Product already exists with name: ${productRequest.name}")
        }
        try {
            return productRepository.save(productRequest.toEntity()).toDto()
        } catch (e: Exception) {
            throw ProductCreationException("Failed to create product")
        }
    }

    fun updateProduct(
        id: Long,
        productRequest: ProductPatchRequest,
    ): ProductResponse {
        val product =
            productRepository.findById(id).getOrNull() ?: throw ProductNotFoundException("Product not found, id: $id")
        val newProduct =
            product.copy(
                name = productRequest.name ?: product.name,
                price = productRequest.price ?: product.price,
                imageUrl = productRequest.imageUrl ?: product.imageUrl,
            )
        try {
            productRepository.save(newProduct)
            return newProduct.toDto()
        } catch (e: Exception) {
            throw ProductUpdateException("Failed to update product, id: $id")
        }
    }

    fun deleteProduct(id: Long) {
        try {
            val deleted = productRepository.deleteById(id)
        } catch (e: Exception) {
            throw ProductNotFoundException("Product not found, id: $id")
        }
    }
}
