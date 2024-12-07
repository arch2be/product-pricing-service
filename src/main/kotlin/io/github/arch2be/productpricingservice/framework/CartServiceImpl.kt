package io.github.arch2be.productpricingservice.framework

import io.github.arch2be.productpricingservice.application.ports.dto.CartResult
import io.github.arch2be.productpricingservice.application.ports.out.CartService
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Cart
import io.github.arch2be.productpricingservice.domain.Discount
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductQuantity
import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountRepository
import io.github.arch2be.productpricingservice.framework.orm.ProductDiscountConfigurationRepository
import io.github.arch2be.productpricingservice.framework.orm.QuantityDiscountRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CartServiceImpl(private val productDiscountConfigurationRepository: ProductDiscountConfigurationRepository,
                      private val quantityDiscountRepository: QuantityDiscountRepository,
                      private val flatDiscountRepository: FlatDiscountRepository,
                      private val productRepository: ProductRepository): CartService {

    override fun loadForProductIdAndQuantity(productId: ProductId, quantity: ProductQuantity): CartResult {
        val product = productRepository.findById(productId) ?: return CartResult.NotFound("product with id: ${productId.value} not found")

        val quantityDiscount = quantityDiscountRepository.findDiscountByProductIdAndQuantity(productId.value, quantity.value) ?: BigDecimal.ZERO
        val flatDiscount = flatDiscountRepository.findDiscountByProductId(productId.value) ?: BigDecimal.ZERO
        val combineType = productDiscountConfigurationRepository.findCombineTypeByProductId(productId.value)

        return CartResult.Success(Cart(product, quantity, listOf(Discount(quantityDiscount), Discount(flatDiscount)), combineType))
    }
}