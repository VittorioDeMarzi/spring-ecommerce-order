package ecommerce.dto

import java.time.LocalDateTime

data class TopProductStats(
    val productName: String,
    val addedCount: Long,
    val lastAddedAt: LocalDateTime,
)
