package ecommerce.service

import ecommerce.client.StripeClient
import ecommerce.dto.OrderRequest
import ecommerce.dto.PaymentResponse
import ecommerce.enum.PaymentStatus
import ecommerce.model.Payment
import ecommerce.repository.PaymentJpaRepository
import ecommerce.repository.getByPaymentIntentIdOrThrow
import jakarta.transaction.Transactional
import org.hibernate.service.spi.ServiceException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Transactional
@Service
class PaymentService(
    private val stripeClient: StripeClient,
    private val paymentJpaRepository: PaymentJpaRepository,
) {
    fun createPaymentIntent(
        request: OrderRequest,
        amount: BigDecimal,
    ): PaymentResponse {
        val paymentResponse =
            stripeClient.createCheckoutSession(request, amount.multiply(BigDecimal.valueOf(100)).toInt())
                ?: throw ServiceException("Payment Failed")

        val payment =
            Payment(
                amount = amount,
                currency = request.currency,
                paymentMethod = request.paymentMethod,
                paymentIntentId = paymentResponse.id,
            )
        paymentJpaRepository.save(payment)
        return paymentResponse
    }

    fun updatePaymentStatus(
        paymentIntentId: String,
        status: PaymentStatus,
    ) {
        val payment = paymentJpaRepository.getByPaymentIntentIdOrThrow(paymentIntentId)
        payment.status = status
        paymentJpaRepository.save(payment)
    }
}
