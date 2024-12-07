package io.github.arch2be.productpricingservice.application.ports


import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import io.github.arch2be.productpricingservice.application.ports.dto.CartResult
import io.github.arch2be.productpricingservice.application.ports.out.CartService
import io.github.arch2be.productpricingservice.application.usecases.CalculateTotalPriceForProductUseCase
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductQuantity
import org.springframework.stereotype.Service

@Service
class CalculateTotalPriceForProductUseCaseService(private val cartService: CartService) : CalculateTotalPriceForProductUseCase {

    override fun calculateTotalPriceBasedOnProductAndQuantity(productId: ProductId, quantity: ProductQuantity) : CalculationResult {
        return when(val cartResult = cartService.loadForProductIdAndQuantity(productId, quantity)) {
            is CartResult.Success -> CalculationResult.Success(cartResult.cart.calculateTotalPrice())
            is CartResult.NotFound -> CalculationResult.NotFound(cartResult.message)
        }
    }
}