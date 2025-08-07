package ecommerce.dto

import java.time.LocalDateTime

data class OrderResponse(
    val paymentIntentId: String,
    val orderId: Long,
    val orderItems: List<OrderItemResponse>,
    val createdAt: LocalDateTime,
    val totalAmount: Double,
)
