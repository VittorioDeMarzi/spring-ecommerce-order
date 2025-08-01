package ecommerce.model

import ecommerce.dto.OptionDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Option(
    val name: String,
    val quantity: Int,
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    fun toDto(): OptionDto {
        return OptionDto(name, quantity)
    }
}
