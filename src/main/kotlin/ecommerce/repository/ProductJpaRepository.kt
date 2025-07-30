package ecommerce.repository

import ecommerce.exception.ProductNotFoundException
import ecommerce.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun ProductJpaRepository.getByIdOrThrow(id: Long): Product =
    findByIdOrNull(id)
        ?: throw ProductNotFoundException("Product not found")

interface ProductJpaRepository : JpaRepository<Product, Long> {
    fun existsByName(name: String): Boolean
}
