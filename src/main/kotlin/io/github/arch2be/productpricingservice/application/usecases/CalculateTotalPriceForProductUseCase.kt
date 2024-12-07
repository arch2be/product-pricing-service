package io.github.arch2be.productpricingservice.application.usecases

import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import java.util.UUID


interface CalculateTotalPriceForProductUseCase {
    fun calculateTotalPriceBasedOnProductAndQuantity(productId: UUID, quantity: Int): CalculationResult
}