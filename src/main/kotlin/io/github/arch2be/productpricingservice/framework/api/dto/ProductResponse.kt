package io.github.arch2be.productpricingservice.framework.api.dto

import java.math.BigDecimal
import java.util.*

data class ProductResponse(val id: UUID, val name: String, val price: BigDecimal)