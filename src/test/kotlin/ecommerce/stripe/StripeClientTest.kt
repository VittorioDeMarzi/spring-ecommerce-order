package ecommerce.stripe

import ecommerce.client.StripeClient
import ecommerce.dto.OrderRequest
import ecommerce.enum.Currency
import ecommerce.exception.StripePaymentException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = [
        "spring.sql.init.mode=never",
        "spring.jpa.hibernate.ddl-auto=none",
    ],
)
class StripeClientTest {
    @Autowired
    private lateinit var stripeClient: StripeClient

    @Test
    fun test1() {
        val actual =
            stripeClient.createCheckoutSession(
                OrderRequest(
                    Currency.EUR,
                    "pm_card_visa",
                ),
                amount = 1000,
            )
        assertThat(actual).isNotNull
        assertThat(actual?.amount).isEqualTo(1000)
    }

    @Test
    fun test_declined_card() {
        assertThatThrownBy {
            stripeClient.createCheckoutSession(
                OrderRequest(
                    currency = Currency.EUR,
                    paymentMethod = "pm_card_chargeDeclined",
                ),
                amount = 1000,
            )
        }
            .isInstanceOf(StripePaymentException::class.java)
            .hasMessageContaining("Your card was declined")
    }

    @Test
    fun test_DeclinedInsufficientFunds() {
        assertThatThrownBy {
            stripeClient.createCheckoutSession(
                OrderRequest(
                    currency = Currency.EUR,
                    paymentMethod = "pm_card_visa_chargeDeclinedInsufficientFunds",
                ),
                amount = 1000,
            )
        }
            .isInstanceOf(StripePaymentException::class.java)
            .hasMessageContaining("insufficient funds")
    }
}
