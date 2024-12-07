package io.github.arch2be.productpricingservice.mock

import io.github.arch2be.productpricingservice.application.ports.out.QuantityDiscountRepository
import io.github.arch2be.productpricingservice.domain.QuantityDiscount
import java.util.*
import kotlin.collections.ArrayList

class QuantityDiscountRepositoryTestImpl: QuantityDiscountRepository {
    private val quantityDiscounts = ArrayList<QuantityDiscount>()

    override fun findByProductIdAndQuantity(productId: UUID, quantity: Int): QuantityDiscount? {
        return quantityDiscounts.find { it.productId === productId && it.min <= quantity && it.max > quantity }
    }

    fun insert(quantityDiscount: QuantityDiscount) {
        quantityDiscounts.add(quantityDiscount)
    }

    fun deleteAll() {
        quantityDiscounts.clear()
    }
}