package ecommerce.repository

import ecommerce.dto.ActiveUsersResponse
import ecommerce.dto.TopProductStats
import ecommerce.model.CartHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartHistoryJpaRepository : JpaRepository<CartHistory, Long> {
    @Query(nativeQuery = true)
    fun getTopProducts(): List<TopProductStats>

    @Query(nativeQuery = true)
    fun getTop5ActiveUsers(): List<ActiveUsersResponse>
}
