package ecommerce.repository

import ecommerce.dto.ActiveUsersResponse
import ecommerce.dto.TopProductStats
import ecommerce.model.CartHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface CartHistoryJpaRepository : JpaRepository<CartHistory, Long> {
    @Query(
        """
        SELECT new ecommerce.dto.TopProductStats(
        p.name,
        COUNT(ch),
        MAX(ch.createdAt)
    )
    FROM CartHistory ch
    JOIN ch.cartProduct ci
    JOIN ci.product p
    WHERE ch.createdAt >= :since
    GROUP BY p.name
    ORDER BY COUNT(ch) DESC, MAX(ch.createdAt) DESC
    """,
    )
    fun getTopProducts(
        @Param("since") since: LocalDateTime,
    ): List<TopProductStats>

    @Query(
        """
    SELECT DISTINCT new ecommerce.dto.ActiveUsersResponse(
        m.id, m.name, m.email
    )
    FROM Member m, Cart c, CartItem ci, CartHistory ch
    WHERE m.id = c.member.id
    AND ci.cart.id = c.id
    AND ch.cartProduct.id = ci.id
    AND ch.createdAt > :since
    ORDER BY m.id
    """,
    )
    fun getTop5ActiveUsers(
        @Param("since") since: LocalDateTime,
    ): List<ActiveUsersResponse>
}
