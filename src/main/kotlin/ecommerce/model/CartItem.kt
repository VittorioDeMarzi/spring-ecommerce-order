package ecommerce.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart,
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,
    val quantity: Int,
)
