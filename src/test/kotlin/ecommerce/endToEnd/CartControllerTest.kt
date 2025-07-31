package ecommerce.endToEnd

import ecommerce.dto.CartItemRequest
import ecommerce.dto.RegistrationRequest
import ecommerce.repository.CartItemJpaRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class CartControllerTest {
    lateinit var token: String

    @Autowired
    private lateinit var cartItemRepository: CartItemJpaRepository

    @BeforeEach
    fun setUp() {
        val registrationRequest =
            RegistrationRequest(
                "test",
                "test1@test.com",
                "12345678",
            )

        token =
            RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(registrationRequest)
                .post("/api/members/register")
                .then().extract().body().jsonPath().getString("token")
    }

    @Test
    fun addToCart() {
        val productToCart =
            CartItemRequest(
                productId = 1,
                quantity = 2,
            )

        val addProduct =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(productToCart)
                .`when`().post("/api/user/wishes")
                .then().log().all().extract()

        Assertions.assertThat(addProduct.statusCode()).isEqualTo(HttpStatus.OK.value())
    }
}
