package io.github.arch2be.productpricingservice.domain

import java.util.UUID

class QuantityDiscount(private val id: UUID,
                       val productId: UUID,
                       val min: Int,
                       val max: Int,
                       private val discount: Double) {

    init {
        if (discount <= 0.0) {
            throw RuntimeException("QuantityDiscount::discount must be greater than zero")
        }
    }

    fun calculateDiscount(baseTotalPrice: Double): Double = baseTotalPrice * (discount / 100)
}