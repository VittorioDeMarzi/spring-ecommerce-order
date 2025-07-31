package ecommerce.model

import ecommerce.dto.ActiveUsersResponse
import ecommerce.dto.TopProductStats
import jakarta.persistence.ColumnResult
import jakarta.persistence.ConstructorResult
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.NamedNativeQuery
import jakarta.persistence.OneToOne
import jakarta.persistence.SqlResultSetMapping
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import java.time.LocalDateTime

@NamedNativeQuery(
    name = "CartHistory.getTopProducts",
    query = """
        SELECT p.name AS productName, COUNT(*) AS addedCount, MAX(ch.created_at) AS lastAddedAt 
        FROM cart_history ch 
        JOIN cart_item ci ON ch.cart_product_id = ci.id 
        JOIN product p ON ci.product_id = p.id 
        WHERE ch.created_at >= CURRENT_DATE - INTERVAL '30' DAY GROUP BY p.id, p.name 
        ORDER BY addedCount DESC, lastAddedAt DESC LIMIT 5;
    """,
    resultSetMapping = "Mapping.TopProductStats",
)
@SqlResultSetMapping(
    name = "Mapping.TopProductStats",
    classes = [
        ConstructorResult(
            targetClass = TopProductStats::class,
            columns = [
                ColumnResult(name = "productName", type = String::class),
                ColumnResult(name = "addedCount", type = Int::class),
                ColumnResult(name = "lastAddedAt", type = Timestamp::class),
            ],
        ),
    ],
)
@NamedNativeQuery(
    name = "CartHistory.getTop5ActiveUsers",
    query = """
        SELECT DISTINCT m.ID AS id, m.NAME AS name, m.EMAIL AS email
        FROM MEMBER m
        JOIN CART c ON m.ID = c.MEMBER_ID
        JOIN CART_ITEM ci ON ci.CART_ID = c.ID
        JOIN CART_HISTORY ch ON ch.CART_PRODUCT_ID = ci.ID
        WHERE ch.CREATED_AT >= CURRENT_DATE - INTERVAL '7' DAY
        ORDER BY m.ID
    """,
    resultSetMapping = "Mapping.TopUserStats",
)
@SqlResultSetMapping(
    name = "Mapping.TopUserStats",
    classes = [
        ConstructorResult(
            targetClass = ActiveUsersResponse::class,
            columns = [
                ColumnResult(name = "ID", type = Long::class),
                ColumnResult(name = "NAME", type = String::class),
                ColumnResult(name = "EMAIL", type = String::class),
            ],
        ),
    ],
)
@Entity
data class CartHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    val cartProduct: CartItem,
    val status: String,
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),
)
