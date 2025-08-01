package ecommerce.model

import ecommerce.repository.OptionJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class OptionTest {
    @Autowired
    private lateinit var optionJpaRepository: OptionJpaRepository

    @Test
    fun reduceOptionQuantity() {
        val option = optionJpaRepository.findById(1).get()
        val quantity = option.quantity
        option.reduceOptionQuantity(1)
        val newQuantity = option.quantity
        assertThat(quantity).isNotEqualTo(newQuantity)
    }
}
