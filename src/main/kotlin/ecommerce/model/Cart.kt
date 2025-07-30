package ecommerce.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @OneToOne
    val member: Member,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "cart")
    val cartProducts: List<CartItem> = listOf<CartItem>(),
) {
    fun addCartItem(cartItem: CartItem) {
        cartProducts.toMutableList().add(cartItem)
    }
}
