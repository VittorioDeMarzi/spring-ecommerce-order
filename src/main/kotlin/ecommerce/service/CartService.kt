package ecommerce.service

import ecommerce.dto.CartItemRequest
import ecommerce.dto.CartItemResponse
import ecommerce.exception.ElementNotFoundException
import ecommerce.model.CartItem
import ecommerce.repository.CartJpaRepository
import ecommerce.repository.ProductJpaRepository
import ecommerce.repository.getByIdOrThrow
import ecommerce.repository.getByMemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CartService(
    private val cartJpaRepository: CartJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
) {
    fun addOrUpdateCartItem(
        memberId: Long,
        request: CartItemRequest,
    ): CartItemResponse {
        val product = productJpaRepository.getByIdOrThrow(request.productId)
        val cart = cartJpaRepository.getByMemberId(memberId)
        val cartItem = CartItem(cart, product, request.quantity)
        cart.addOrUpdateCartItem(cartItem)
        cartJpaRepository.save(cart)
        return cartItem.toDto()
    }

    fun getCartItems(memberId: Long): List<CartItemResponse> {
        val cart = cartJpaRepository.getByMemberId(memberId)
        return cart.cartProducts.map { it.toDto() }
    }

    fun deleteProductFromCart(
        memberId: Long,
        productId: Long,
    ) {
        val cart = cartJpaRepository.getByMemberId(memberId)
        val deleted = cart.deleteCartProduct(productId)
        if (!deleted) throw ElementNotFoundException("Element not in the cart")
        cartJpaRepository.save(cart)
    }
}
