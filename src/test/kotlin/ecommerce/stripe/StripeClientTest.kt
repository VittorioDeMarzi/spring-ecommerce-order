package ecommerce.stripe

import ecommerce.client.StripeClient
import ecommerce.dto.PaymentRequest
import ecommerce.enum.Currency
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StripeClientTest {
    @Autowired
    private lateinit var stripeClient: StripeClient

    @Test
    fun test1() {
        println("STRIPE_SECRET_KEY: ${System.getenv("STRIPE_SECRET_KEY")}")
        val actual =
            stripeClient.createCheckoutSession(
                PaymentRequest(
                    1000,
                    Currency.EUR,
                    "pm_card_visa",
                ),
            )
        assertThat(actual).isNotNull
    }
}
