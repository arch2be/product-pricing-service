package io.github.arch2be.productpricingservice.application.ports.dto

sealed class CalculationResult {
    data class Success(val totalPrice: Double): CalculationResult()
    data class NotFound(val message: String): CalculationResult()
    data class Error(val message: String): CalculationResult()
}