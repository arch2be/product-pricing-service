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

class QuantityDiscountRepositoryImplTest: ShouldSpec({
    val quantityRepository = QuantityDiscountRepository()

    beforeAny {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(QuantityDiscountEntity, ProductEntity)
        }
    }

    afterAny {
        transaction {
            QuantityDiscountEntity.deleteAll()
            ProductEntity.deleteAll()
            SchemaUtils.drop(QuantityDiscountEntity, ProductEntity)
        }
    }

    should("should return null result when quantity discount not exists in DB") {
        // Then:
        quantityRepository.findDiscountByProductIdAndQuantity(UUID.randomUUID(), 1) shouldBe null
    }

    should("should return correct discount when quantity discount exists in DB") {
        // Given:
        val productId = UUID.randomUUID()
        val quantity = 20
        val expectedDiscount = BigDecimal(10.0)

        // When:
        transaction {
            ProductEntity.insert {
                it[this.id] = productId
                it[this.name] = "name"
                it[this.price] = BigDecimal(10)
            }

            QuantityDiscountEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = productId
                it[this.min] = 1
                it[this.max] = 20
                it[this.discount] = 10.0
            }

            QuantityDiscountEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = productId
                it[this.min] = 21
                it[this.max] = 30
                it[this.discount] = 15.0
            }
        }

        // Then:
        quantityRepository.findDiscountByProductIdAndQuantity(productId, quantity) shouldBe expectedDiscount
    }
})