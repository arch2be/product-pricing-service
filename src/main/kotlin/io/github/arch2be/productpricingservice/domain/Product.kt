package io.github.arch2be.productpricingservice.domain

import java.util.UUID

class Product(val id: UUID, private val name: String, val price: Double) {
    init {
        if (price <= 0.0) {
            throw RuntimeException("Product::price must be greater than zero")
        }
    }
}