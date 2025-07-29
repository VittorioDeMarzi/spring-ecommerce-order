package ecommerce.jpaStudy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


interface StationRepository : JpaRepository<Station, Long> {
    fun findByName(name: String): Optional<Station>
}
