package ecommerce.model

import ecommerce.dto.ProductResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val name: String,
    @Column(nullable = false)
    val price: Double,
    val imageUrl: String,
)

fun Product.toDto(): ProductResponse {
    return ProductResponse(id!!, name, price, imageUrl)
}
