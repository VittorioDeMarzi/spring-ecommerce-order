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
import ecommerce.repository.ProductJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ProductService(private val productJpaRepository: ProductJpaRepository) {
    fun findById(id: Long): ProductResponse {
        val product =
            productJpaRepository.findById(id).getOrNull() ?: throw ProductNotFoundException("Product not found, id: $id")
        return product.toDto()
    }

    fun findAll(pageable: Pageable): Page<ProductResponse> {
        val products = productJpaRepository.findAll(pageable)

        return products.map { product -> product.toDto() }
    }

    fun createProduct(productRequest: ProductRequest): ProductResponse {
        if (productJpaRepository.existsByName(productRequest.name)) {
            throw ProductAlreadyInDBException("Product already exists with name: ${productRequest.name}")
        }
        try {
            return productJpaRepository.save(productRequest.toEntity()).toDto()
        } catch (e: Exception) {
            throw ProductCreationException("Failed to create product")
        }
    }

    fun updateProduct(
        id: Long,
        productRequest: ProductPatchRequest,
    ): ProductResponse {
        val product =
            productJpaRepository.findById(id).getOrNull() ?: throw ProductNotFoundException("Product not found, id: $id")
        val newProduct =
            product.copy(
                name = productRequest.name ?: product.name,
                price = productRequest.price ?: product.price,
                imageUrl = productRequest.imageUrl ?: product.imageUrl,
                options = product.options,
            )
        try {
            productJpaRepository.save(newProduct)
            return newProduct.toDto()
        } catch (e: Exception) {
            throw ProductUpdateException("Failed to update product, id: $id")
        }
    }

    fun deleteProduct(id: Long) {
        try {
            val deleted = productJpaRepository.deleteById(id)
        } catch (e: Exception) {
            throw ProductNotFoundException("Product not found, id: $id")
        }
    }
}
