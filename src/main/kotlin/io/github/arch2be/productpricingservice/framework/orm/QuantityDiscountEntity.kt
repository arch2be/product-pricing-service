package io.github.arch2be.productpricingservice.framework.orm

import org.jetbrains.exposed.dao.id.UUIDTable

object QuantityDiscountEntity: UUIDTable("quantity_discount") {
    val productId = reference("products", ProductEntity)
    val min = integer("min")
    val max = integer("max")
    val discount = double("discount")
}