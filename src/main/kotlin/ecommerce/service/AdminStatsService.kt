package ecommerce.service

import ecommerce.dto.ActiveUsersResponse
import ecommerce.dto.TopProductStats
import ecommerce.repository.CartHistoryJpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AdminStatsService(
    private val cartHistoryJpaRepository: CartHistoryJpaRepository,
) {
    fun getTopProducts(): List<TopProductStats> {
        return cartHistoryJpaRepository.getTopProducts(LocalDateTime.now().minusDays(LAST_MONTH))
    }

    fun getTopActiveUsers(): List<ActiveUsersResponse> {
        return cartHistoryJpaRepository.getTop5ActiveUsers(LocalDateTime.now().minusDays(LAST_WEEK))
    }

    companion object {
        private const val LAST_MONTH = 30L
        private const val LAST_WEEK = 7L
    }
}
