package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.domain.CombiningType
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductDiscountConfigurationRepository {

    fun findCombineTypeByProductId(productId: UUID): CombiningType? {
        return transaction {
            ProductDiscountConfigurationEntity
                .selectAll()
                .where { ProductDiscountConfigurationEntity.id eq productId }
                .firstOrNull()
                ?.let { it[ProductDiscountConfigurationEntity.combiningType] }
        }
    }
}