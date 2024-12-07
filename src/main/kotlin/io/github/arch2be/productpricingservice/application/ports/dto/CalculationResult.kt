package io.github.arch2be.productpricingservice.application.ports.dto

import java.math.BigDecimal

sealed class CalculationResult {
    data class Success(val totalPrice: BigDecimal): CalculationResult()
    data class NotFound(val message: String): CalculationResult()
}