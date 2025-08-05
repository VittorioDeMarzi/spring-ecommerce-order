package ecommerce.model

import ecommerce.dto.enum.CartHistoryStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
data class CartHistory(
    @ManyToOne(fetch = FetchType.LAZY)
    var member: Member,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var product: Product,
    var quantity: Int,
    @Enumerated(EnumType.STRING)
    val status: CartHistoryStatus = CartHistoryStatus.ADDED,
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
)
