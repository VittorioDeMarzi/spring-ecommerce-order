package ecommerce.client

import com.fasterxml.jackson.databind.ObjectMapper
import ecommerce.configuration.StripeProperties
import ecommerce.dto.OrderRequest
import ecommerce.dto.PaymentResponse
import ecommerce.exception.StripeErrorInfo
import ecommerce.exception.StripePaymentException
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException

@Component
class StripeClient(
    private val stripeProperties: StripeProperties,
) {
    private val restClient = RestClient.create()

    fun createCheckoutSession(
        req: OrderRequest,
        amount: Int,
    ): PaymentResponse? {
        val body =
            listOf(
                "amount=$amount",
                "currency=${req.currency}",
                "payment_method=${req.paymentMethod}",
                "confirm=true",
                "automatic_payment_methods[enabled]=true",
                "automatic_payment_methods[allow_redirects]=never",
            ).joinToString("&")

        return try {
            println(stripeProperties.secretKey)
            val response =
                restClient.post()
                    .uri("https://api.stripe.com/v1/payment_intents")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${stripeProperties.secretKey}")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .toEntity(PaymentResponse::class.java)

            response.body
        } catch (e: RestClientResponseException) {
            val errorInfo = parseStripeError(e.responseBodyAsString)
            throw StripePaymentException(
                "Stripe error: ${errorInfo.message} (code: ${errorInfo.code})",
                e,
            )
        } catch (e: Exception) {
            throw StripePaymentException("Unexpected Stripe error: ${e.message}", e)
        }
    }

    fun parseStripeError(json: String?): StripeErrorInfo {
        return try {
            val obj = ObjectMapper().readTree(json)
            StripeErrorInfo(
                message = obj.get("error").get("message").asText(),
                code = obj.get("error").get("code").asText(),
            )
        } catch (e: Exception) {
            StripeErrorInfo(message = "Unable to parse Stripe error: ${e.message}")
        }
    }
}
