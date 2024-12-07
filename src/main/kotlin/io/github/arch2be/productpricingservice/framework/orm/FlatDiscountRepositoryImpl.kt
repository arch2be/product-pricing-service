package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.application.ports.out.FlatDiscountRepository
import io.github.arch2be.productpricingservice.domain.FlatDiscount
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class FlatDiscountRepositoryImpl: FlatDiscountRepository {

    override fun findByProductId(productId: UUID): FlatDiscount? {
        return transaction {
            FlatDiscountEntity
                .selectAll()
                .where { FlatDiscountEntity.productId eq productId }
                .firstOrNull()
                ?.let {
                    FlatDiscount(
                        it[FlatDiscountEntity.id].value,
                        it[FlatDiscountEntity.productId].value,
                        it[FlatDiscountEntity.discount])
                }
        }
    }
}