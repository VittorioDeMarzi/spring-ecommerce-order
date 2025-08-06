package ecommerce.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "customer_orders")
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,
    @OneToMany(cascade = [CascadeType.ALL])
    val orderItems: List<OrderItem>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
)
