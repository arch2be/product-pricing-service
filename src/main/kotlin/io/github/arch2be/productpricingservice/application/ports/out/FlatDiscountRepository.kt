package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.domain.FlatDiscount
import java.util.UUID

interface FlatDiscountRepository {
    fun findByProductId(productId: UUID): FlatDiscount?
}