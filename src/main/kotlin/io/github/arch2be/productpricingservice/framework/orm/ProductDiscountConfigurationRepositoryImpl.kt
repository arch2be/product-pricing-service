package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.application.ports.out.ProductDiscountConfigurationRepository
import io.github.arch2be.productpricingservice.domain.ProductDiscountConfiguration
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductDiscountConfigurationRepositoryImpl: ProductDiscountConfigurationRepository {

    override fun findByProductId(productId: UUID): ProductDiscountConfiguration? {
        return transaction {
            ProductDiscountConfigurationEntity
                .selectAll()
                .where { ProductDiscountConfigurationEntity.id eq productId }
                .firstOrNull()
                ?.let {
                    ProductDiscountConfiguration(
                        it[ProductDiscountConfigurationEntity.id].value,
                        it[ProductDiscountConfigurationEntity.productId].value,
                        it[ProductDiscountConfigurationEntity.combiningType])
                }
        }
    }
}