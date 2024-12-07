package io.github.arch2be.productpricingservice.application.ports


import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import io.github.arch2be.productpricingservice.application.ports.out.FlatDiscountRepository
import io.github.arch2be.productpricingservice.application.ports.out.ProductDiscountConfigurationRepository
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.application.ports.out.QuantityDiscountRepository
import io.github.arch2be.productpricingservice.application.usecases.CalculateTotalPriceForProductUseCase
import io.github.arch2be.productpricingservice.domain.CombiningType
import org.springframework.stereotype.Service
import java.util.*

@Service
class CalculateTotalPriceForProductUseCaseService(
    private val productDiscountConfigurationRepository: ProductDiscountConfigurationRepository,
    private val quantityDiscountRepository: QuantityDiscountRepository,
    private val flatDiscountRepository: FlatDiscountRepository,
    private val productRepository: ProductRepository
) : CalculateTotalPriceForProductUseCase {

    override fun calculateTotalPriceBasedOnProductAndQuantity(productId: UUID, quantity: Int): CalculationResult {
        if (quantity <= 0) {
            return CalculationResult.Error("quantity must be greater than zero.")
        }

        val product = productRepository.findById(productId)

        if (product === null) {
            return CalculationResult.NotFound("not found product by id: $productId")
        }

        val baseTotalPrice = product.price * quantity

        val flatDiscount = flatDiscountRepository.findByProductId(productId)
            ?.calculateDiscount(baseTotalPrice) ?: 0.0

        val quantityDiscount = quantityDiscountRepository.findByProductIdAndQuantity(productId, quantity)
            ?.calculateDiscount(baseTotalPrice) ?: 0.0

        val combiningType = productDiscountConfigurationRepository.findByProductId(productId)?.combiningType
            ?: CombiningType.CHOOSE_THE_BEST

        return CalculationResult.Success(baseTotalPrice - combiningType.combine(listOf(flatDiscount, quantityDiscount)))
    }
}