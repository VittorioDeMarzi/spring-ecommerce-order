package ecommerce.service

import ecommerce.dto.CartItemRequest
import ecommerce.dto.CartItemResponse
import ecommerce.exception.ElementNotFoundException
import ecommerce.model.CartItem
import ecommerce.repository.CartItemRepository
import ecommerce.repository.CartJpaRepository
import ecommerce.repository.CartRepository
import ecommerce.repository.ProductJpaRepository
import ecommerce.repository.getByIdOrThrow
import ecommerce.repository.getByMemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val cartJpaRepository: CartJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
) {
    fun addCartItem(
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
        val cart = cartRepository.findCartByMemberId(memberId) ?: throw ElementNotFoundException("Cart Not Found")
        return cartItemRepository.getCartItemsByCartId(cart.id)
    }

    fun deleteProductFromCart(
        memberId: Long,
        productId: Long,
    ): Boolean {
        val cart = cartRepository.findCartByMemberId(memberId) ?: throw ElementNotFoundException("Cart Not Found")
        return cartItemRepository.deleteCartItemsByCartIdAndProductId(
            cart.id,
            productId,
        )
    }

    fun updateCartItemQuantity(
        memberId: Long,
        productId: Long,
        quantity: Int,
    ): Boolean {
        val cart = cartRepository.findCartByMemberId(memberId) ?: throw ElementNotFoundException("Cart Not Found")
        cartItemRepository.findByCartIdAndProductId(cart.id, productId)
            ?: throw ElementNotFoundException("Product Not Found in Cart")

        return if (quantity == 0) {
            cartItemRepository.deleteCartItemsByCartIdAndProductId(cart.id, productId)
        } else {
            cartItemRepository.updateQuantityByCartIdAndProductId(cart.id, productId, quantity)
        }
    }
}
