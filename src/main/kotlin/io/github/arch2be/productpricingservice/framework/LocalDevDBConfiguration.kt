package io.github.arch2be.productpricingservice.framework

import io.github.arch2be.productpricingservice.domain.*
import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductDiscountConfigurationEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductEntity
import io.github.arch2be.productpricingservice.framework.orm.QuantityDiscountEntity
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Profile(value = ["dev", "dev-docker"])
@Component
class LocalDevDBConfiguration: ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val productId = ProductId(UUID.fromString("7a81cfcd-c25b-4b65-9ea4-8eca89b39316"))
        val product = Product(productId, ProductName("test-name"), ProductPrice(BigDecimal(10)))

        transaction {
            SchemaUtils.create(
                ProductEntity,
                ProductDiscountConfigurationEntity,
                FlatDiscountEntity,
                QuantityDiscountEntity
            )

            ProductEntity.insert {
                it[this.id] = product.id.value
                it[this.name] = product.name.value
                it[this.price] = product.price.value
            }

            FlatDiscountEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = product.id.value
                it[this.discount] = 10.0
            }

            QuantityDiscountEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = product.id.value
                it[this.min] = 1
                it[this.max] = 20
                it[this.discount] = 20.0
            }

            ProductDiscountConfigurationEntity.insert {
                it[this.id] = UUID.randomUUID()
                it[this.productId] = product.id.value
                it[this.combiningType] = CombiningType.CUMULATIVE
            }
        }
    }
}