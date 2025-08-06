package ecommerce.dto

import ecommerce.enum.Currency

class PaymentRequest(
    val amount: Int,
    val currency: Currency,
    val paymentMethod: String,
)
