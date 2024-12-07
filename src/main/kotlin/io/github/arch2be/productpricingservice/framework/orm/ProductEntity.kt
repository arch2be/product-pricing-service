package io.github.arch2be.productpricingservice.framework.orm

import org.jetbrains.exposed.dao.id.UUIDTable

object ProductEntity: UUIDTable("products") {
    val name = text("name")
    val price = decimal("price", 5, 2)
}