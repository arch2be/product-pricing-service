package io.github.arch2be.productpricingservice.application.usecases

import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductQuantity


interface CalculateTotalPriceForProductUseCase {
    fun calculateTotalPriceBasedOnProductAndQuantity(productId: ProductId, quantity: ProductQuantity): CalculationResult
}