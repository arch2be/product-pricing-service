package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.domain.CombiningType
import org.jetbrains.exposed.dao.id.UUIDTable

object ProductDiscountConfigurationEntity: UUIDTable("product_discount_configurations") {
    val productId = reference("products", ProductEntity)
    val combiningType = enumeration<CombiningType>("combiningType")
}