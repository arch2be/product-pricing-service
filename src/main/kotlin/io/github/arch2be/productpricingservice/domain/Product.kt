package io.github.arch2be.productpricingservice.domain

import java.math.BigDecimal
import java.util.*

data class Product(val id: ProductId, val name: ProductName, val price: ProductPrice)

data class ProductId(val value: UUID)

data class ProductName(val value: String)

data class ProductPrice(val value: BigDecimal) {
    init {
        require(value > BigDecimal.ZERO) { "ProductPrice.value must be greater than zero" }
    }
}