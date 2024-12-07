package io.github.arch2be.productpricingservice.domain

import java.util.UUID

class FlatDiscount(private val id: UUID, private val productId: UUID, private val discount: Double) {

    init {
        if (discount <= 0.0) {
            throw RuntimeException("FlatDiscount::discount must be greater than zero")
        }
    }

    fun calculateDiscount(baseTotalPrice: Double): Double = baseTotalPrice * (discount / 100)
}