package io.github.arch2be.productpricingservice.mock

import io.github.arch2be.productpricingservice.application.ports.out.FlatDiscountRepository
import io.github.arch2be.productpricingservice.domain.FlatDiscount
import java.util.*

class FlatDiscountRepositoryTestImpl: FlatDiscountRepository {
    private val flatDiscounts = HashMap<UUID, FlatDiscount>()

    override fun findByProductId(productId: UUID): FlatDiscount? = flatDiscounts[productId]

    fun insert(flatDiscount: FlatDiscount) {
        flatDiscounts[flatDiscount.productId] = flatDiscount
    }

    fun deleteAll() {
        flatDiscounts.clear()
    }
}