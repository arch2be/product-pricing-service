package io.github.arch2be.productpricingservice.framework.orm

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.UUID

@Repository
class QuantityDiscountRepository {

    fun findDiscountByProductIdAndQuantity(productId: UUID, quantity: Int): BigDecimal? {
        return transaction {
            QuantityDiscountEntity
                .selectAll()
                .where { (QuantityDiscountEntity.productId eq productId) and (QuantityDiscountEntity.min.greaterEq(quantity) and QuantityDiscountEntity.max.less(quantity))}
                .firstOrNull()
                ?.let { BigDecimal(it[QuantityDiscountEntity.discount]) }
        }
    }
}