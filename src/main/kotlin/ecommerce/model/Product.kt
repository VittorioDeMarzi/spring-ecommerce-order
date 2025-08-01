package ecommerce.model

import ecommerce.dto.ProductResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Product(
    @Column(nullable = false, unique = true)
    val name: String,
    @OneToMany(mappedBy = "product")
    val options: MutableList<Option> = mutableListOf(),
    @Column(nullable = false)
    val price: Double,
    val imageUrl: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    init {
        require(options.isNotEmpty())
    }
}

fun Product.toDto(): ProductResponse {
    return ProductResponse(id, name, price, imageUrl)
}
