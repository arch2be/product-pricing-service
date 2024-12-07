package io.github.arch2be.productpricingservice.domain

import java.util.UUID

class ProductDiscountConfiguration(private val id: UUID, val productId: UUID, val combiningType: CombiningType)