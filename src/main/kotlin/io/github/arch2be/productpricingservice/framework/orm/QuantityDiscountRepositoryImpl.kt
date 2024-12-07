package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.application.ports.out.QuantityDiscountRepository
import io.github.arch2be.productpricingservice.domain.QuantityDiscount
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class QuantityDiscountRepositoryImpl: QuantityDiscountRepository {

    override fun findByProductIdAndQuantity(productId: UUID, quantity: Int): QuantityDiscount? {
        return transaction {
            QuantityDiscountEntity
                .selectAll()
                .where { (QuantityDiscountEntity.productId eq productId) and (QuantityDiscountEntity.min.greaterEq(quantity) and QuantityDiscountEntity.max.less(quantity))}
                .firstOrNull()
                ?.let {
                    QuantityDiscount(
                        it[QuantityDiscountEntity.id].value,
                        it[QuantityDiscountEntity.productId].value,
                        it[QuantityDiscountEntity.min],
                        it[QuantityDiscountEntity.max],
                        it[QuantityDiscountEntity.discount])
                }
        }
    }
}