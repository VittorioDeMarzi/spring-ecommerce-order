package ecommerce.repository

import ecommerce.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
    fun existsByName(name: String): Boolean
}
