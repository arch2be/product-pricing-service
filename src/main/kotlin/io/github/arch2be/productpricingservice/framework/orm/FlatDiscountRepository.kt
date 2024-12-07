package io.github.arch2be.productpricingservice.framework.orm

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
class FlatDiscountRepository {

    fun findDiscountByProductId(productId: UUID): BigDecimal? {
        return transaction {
            FlatDiscountEntity
                .selectAll()
                .where { FlatDiscountEntity.productId eq productId }
                .firstOrNull()
                ?.let { BigDecimal(it[FlatDiscountEntity.discount]) }
        }
    }
}