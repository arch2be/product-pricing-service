package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.DatabaseTestContainer.dataSource
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.util.*


class FlatDiscountRepositoryImplTest: ShouldSpec({
    val flatDiscountRepository = FlatDiscountRepository()

    beforeAny {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(FlatDiscountEntity, ProductEntity)
        }
    }

    afterAny {
        transaction {
            FlatDiscountEntity.deleteAll()
            ProductEntity.deleteAll()
            SchemaUtils.drop(FlatDiscountEntity, ProductEntity)
        }
    }

    should("should return null result when flat discount not exists in DB") {
        flatDiscountRepository.findDiscountByProductId(UUID.randomUUID()) shouldBe null
    }

    should("should return correct discount when flat discount exists in DB") {
        // Given:
        val productId = UUID.randomUUID()
        val expectedDiscount = BigDecimal(10.0)

        // When:
        transaction {
            ProductEntity.insert {
                it[this.id] = productId
                it[this.name] = "name"
                it[this.price] = BigDecimal(10)
            }

            FlatDiscountEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = productId
                it[this.discount] = 10.0
            }
        }

        // Then:
        flatDiscountRepository.findDiscountByProductId(productId) shouldBe expectedDiscount
    }
})