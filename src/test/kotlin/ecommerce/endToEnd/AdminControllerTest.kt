package ecommerce.endToEnd

import ecommerce.dto.LoginRequest
import ecommerce.dto.ProductRequest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {
    lateinit var token: String

    @BeforeEach
    fun setUp() {
        val loginRequest =
            LoginRequest(
                "admin@test.com",
                "12345678",
            )

        val response =
            RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/api/members/login")
                .then().extract()

        token = response.body().jsonPath().getString("token")
    }

    @Test
    fun getAllProducts() {
        val response =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .`when`().get("/api/admin/products")
                .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        val names = response.body().jsonPath().getList<String>("")
        assertThat(names).isNotEmpty()
        assertThat(names.size).isEqualTo(10)
    }

    @Test
    fun getProductById() {
        val response =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .`when`().get("/api/admin/products/1")
                .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        val productName = response.body().jsonPath().getString("name")
        assertThat(productName).isEqualTo("Espresso")
    }

    @Test
    fun createProduct() {
        val productRequest =
            ProductRequest(
                "newProductTest",
                2.99,
                "http://www.newProduct.jpg",
            )

        val response =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(productRequest)
                .post("/api/admin/products")
                .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val productName = response.body().jsonPath().getString("name")
        assertThat(productName).isEqualTo("newProductTest")
    }

    @Test
    fun updateProduct() {
        val productRequest =
            ProductRequest(
                "updatedTest",
                2.99,
                "http://www.newProduct.jpg",
            )

        val response =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(productRequest)
                .patch("/api/admin/products/1")
                .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val productName = response.body().jsonPath().getString("name")
        assertThat(productName).isEqualTo("updatedTest")
    }

    @Test
    fun deleteProduct() {
        val response =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete("/api/admin/products/1")
                .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())

        val deleted =
            RestAssured.given().log().all()
                .auth().oauth2(token)
                .accept(ContentType.JSON)
                .`when`().get("/api/admin/products/1")
                .then().log().all().extract()

        assertThat(deleted.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
    }
}
