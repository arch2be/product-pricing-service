package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.domain.QuantityDiscount
import java.util.UUID

interface QuantityDiscountRepository {
    fun findByProductIdAndQuantity(productId: UUID, quantity: Int): QuantityDiscount?
}