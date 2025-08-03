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
    FROM CartHistory ch, CartItem ci, Product p
    WHERE ch.cartProduct.id = ci.id 
    AND ci.product.id = p.id 
    AND ch.createdAt >= :since
    GROUP BY p.name
    ORDER BY COUNT(ch) DESC, MAX(ch.createdAt) DESC
    """,
    )
    fun getTopProducts(
        @Param("since") since: LocalDateTime,
    ): List<TopProductStats>

    @Query(nativeQuery = true)
    fun getTop5ActiveUsers(): List<ActiveUsersResponse>
}
