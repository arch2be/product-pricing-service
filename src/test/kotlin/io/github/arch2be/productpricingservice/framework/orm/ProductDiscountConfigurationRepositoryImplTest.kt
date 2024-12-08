package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.DatabaseTestContainer.dataSource
import io.github.arch2be.productpricingservice.domain.CombiningType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.util.*

class ProductDiscountConfigurationRepositoryImplTest: ShouldSpec({
    val productDiscountConfigurationRepository = ProductDiscountConfigurationRepository()

    beforeAny {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(ProductEntity, ProductDiscountConfigurationEntity)
        }
    }

    afterAny {
        transaction {
            ProductDiscountConfigurationEntity.deleteAll()
            ProductEntity.deleteAll()
            SchemaUtils.drop(ProductEntity, ProductDiscountConfigurationEntity)
        }
    }

    should("should return null result when product discount configuration not exists in DB") {
        productDiscountConfigurationRepository.findCombineTypeByProductId(UUID.randomUUID()) shouldBe null
    }

    should("should return correct combine type when product discount configuration exists in DB") {
        // Given:
        val productId = UUID.randomUUID()
        val expectedCombingType = CombiningType.CUMULATIVE

        // When:
        transaction {
            ProductEntity.insert {
                it[this.id] = productId
                it[this.name] = "name"
                it[this.price] = BigDecimal(10)
            }

            ProductDiscountConfigurationEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = productId
                it[this.combiningType] = expectedCombingType
            }
        }

        productDiscountConfigurationRepository.findCombineTypeByProductId(productId) shouldBe expectedCombingType
    }
})