package io.github.arch2be.productpricingservice.framework.orm

import org.jetbrains.exposed.dao.id.UUIDTable

object FlatDiscountEntity: UUIDTable("flat_discounts") {
    val productId = reference("products", ProductEntity)
    val discount = double("discount")
}