package ecommerce.dto

data class PaymentResponse(
    val id: String,
    val amount: Int,
    val status: String,
)
