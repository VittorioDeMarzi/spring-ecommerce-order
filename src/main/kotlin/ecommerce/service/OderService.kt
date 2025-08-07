package ecommerce.service

import ecommerce.client.StripeClient
import ecommerce.dto.OrderRequest
import ecommerce.dto.OrderResponse
import ecommerce.enum.OrderStatus
import ecommerce.mapper.toOrderItem
import ecommerce.mapper.toOrderResponse
import ecommerce.model.Cart
import ecommerce.model.Order
import ecommerce.repository.CartJpaRepository
import ecommerce.repository.OrderJpaRepository
import ecommerce.repository.getByMemberId
import org.hibernate.service.spi.ServiceException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OderService(
    private val cartJpaRepository: CartJpaRepository,
    private val stripeClient: StripeClient,
    private val orderJpaRepository: OrderJpaRepository,
) {
    fun processOrder(
        memberId: Long,
        request: OrderRequest,
    ): OrderResponse {
        val cart = cartJpaRepository.getByMemberId(memberId)
        val amount = cart.cartProducts.sumOf { it.option.product!!.price }
        val paymentIntent =
            stripeClient.createCheckoutSession(request, amount.multiply(BigDecimal.valueOf(100)).toInt())
                ?: throw ServiceException("Payment Failed")

        val newOrder = placeOrder(cart, memberId)
        if (paymentIntent.status == "succeeded") {
            newOrder.status == OrderStatus.PAID
        }
        return newOrder.toOrderResponse()
    }

    private fun placeOrder(
        cart: Cart,
        memberId: Long,
    ): Order {
        val amount = cart.cartProducts.sumOf { it.option.product!!.price }
        val orderItems = cart.cartProducts.map { it.toOrderItem() }
        return orderJpaRepository.save(Order(memberId, orderItems, amount))
    }
}
