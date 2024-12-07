package io.github.arch2be.productpricingservice.framework

import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductDiscountConfigurationEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductEntity
import io.github.arch2be.productpricingservice.framework.orm.QuantityDiscountEntity
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class LocalDevTestConfiguration: ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        SchemaUtils.create(
            ProductEntity,
            FlatDiscountEntity,
            QuantityDiscountEntity,
            ProductDiscountConfigurationEntity)
    }
}