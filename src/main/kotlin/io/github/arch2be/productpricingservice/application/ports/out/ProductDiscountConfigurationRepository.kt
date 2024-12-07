package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.domain.ProductDiscountConfiguration
import java.util.*

interface ProductDiscountConfigurationRepository {
    fun findByProductId(productId: UUID): ProductDiscountConfiguration?
}