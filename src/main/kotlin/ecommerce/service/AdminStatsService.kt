package ecommerce.service

import ecommerce.dto.ActiveUsersResponse
import ecommerce.dto.TopProductStats
import ecommerce.repository.CartHistoryJpaRepository
import org.springframework.stereotype.Service

@Service
class AdminStatsService(
    private val cartHistoryJpaRepository: CartHistoryJpaRepository,
) {
    fun getTopProducts(): List<TopProductStats> {
        return cartHistoryJpaRepository.getTopProducts()
    }

    fun getTopActiveUsers(): List<ActiveUsersResponse> {
        return cartHistoryJpaRepository.getTop5ActiveUsers()
    }
}
