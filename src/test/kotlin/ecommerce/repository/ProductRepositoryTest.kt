package ecommerce.repository

import ecommerce.model.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @Test
    fun save() {
        val expected = Product(null, "test", 10.0, "https://google.com")
        val actual = productRepository.save(expected)
        assertThat(actual.id).isNotNull
        assertThat(actual.id).isNotZero
        assertThat(actual.name).isEqualTo(expected.name)
    }

    @Test
    fun findAll() {
        val expected = Product(null, "test", 10.0, "https://google.com")
        val expected2 = Product(null, "test2", 10.0, "https://google.com")
        productRepository.save(expected)
        productRepository.save(expected2)
        val products = productRepository.findAll()
        assertThat(products).hasSize(2)
    }

    @Test
    fun findFyId() {
        val expected = Product(null, "test", 10.0, "https://google.com")
        productRepository.save(expected)
        val product = productRepository.findById(1).get()
        assertThat(product.name).isEqualTo("test")
    }

    @Test
    fun existByName() {
        val expected = Product(null, "test", 10.0, "https://google.com")
        productRepository.save(expected)
        val product = productRepository.existsByName("test")
        assertThat(product).isTrue
    }
}
