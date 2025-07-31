package ecommerce.model

import ecommerce.dto.MemberDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    fun toDto(): MemberDto {
        return MemberDto(id, email, role)
    }
}
