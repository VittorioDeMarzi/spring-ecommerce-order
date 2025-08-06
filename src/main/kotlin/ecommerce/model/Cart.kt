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
    @OneToOne(cascade = [CascadeType.PERSIST])
    val member: Member,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "cart", orphanRemoval = true)
    val cartProducts: MutableList<CartItem> = mutableListOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    fun addOrUpdateCartItem(cartItem: CartItem) {
        val presentOption =
            cartProducts.firstOrNull { it.option.id == cartItem.option.id }
        when (presentOption) {
            null -> {
                cartProducts.add(cartItem)
                cartItem.cart = this
            }
            else -> presentOption.quantity = cartItem.quantity
        }
    }

    fun deleteCartProduct(optionId: Long): Boolean {
        return cartProducts
            .removeIf { cartItem ->
                (cartItem.option.id == optionId)
                    .also {
                        if (it) cartItem.cart = null
                    }
            }
    }
}
