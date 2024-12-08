package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.DatabaseTestContainer.dataSource
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductName
import io.github.arch2be.productpricingservice.domain.ProductPrice
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.util.*

class ProductRepositoryImplTest: ShouldSpec({
    val productRepository = ProductRepositoryImpl()

    beforeAny {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(ProductEntity)
        }
    }

    afterAny {
        transaction {
            ProductEntity.deleteAll()
            SchemaUtils.drop(ProductEntity)
        }
    }

    should("should return null result when product not exists in DB") {
        productRepository.findById(ProductId(UUID.randomUUID())) shouldBe null
    }

    should("should return correct product when exists in DB") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val expectedProduct = Product(productId, ProductName("name"), ProductPrice(BigDecimal("10.00")))

        // When:
        transaction {
            ProductEntity.insert {
                it[this.id] = expectedProduct.id.value
                it[this.name] = expectedProduct.name.value
                it[this.price] = expectedProduct.price.value
            }
        }

        // Then:
        productRepository.findById(productId) shouldBe expectedProduct
    }
})