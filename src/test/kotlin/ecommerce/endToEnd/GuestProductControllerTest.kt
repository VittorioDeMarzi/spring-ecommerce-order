package ecommerce.endToEnd

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class GuestProductControllerTest {
    @Test
    fun getProducts() {
        val response =
            RestAssured
                .given().log().all()
                .`when`()
                .request("GET", "/api/products")
                .then()
                .extract()
                .response()

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK.value())
        val products = response.body().jsonPath().getList<String>("")
        Assertions.assertThat(products).isNotEmpty()
        Assertions.assertThat(products.size).isEqualTo(10)
    }

    @Test
    fun getProduct() {
        val response =
            RestAssured
                .given().log().all()
                .`when`()
                .request("GET", "/api/products/1")
                .then()
                .extract()
                .response()

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK.value())
        val productName = response.body().jsonPath().getString("name")
        Assertions.assertThat(productName).isEqualTo("Espresso")
    }
}
