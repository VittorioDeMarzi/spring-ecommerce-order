package ecommerce.model

import ecommerce.dto.enum.CartHistoryStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
data class CartHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    val cartProduct: CartItem,
    @Enumerated(EnumType.STRING)
    val status: CartHistoryStatus,
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),
)
