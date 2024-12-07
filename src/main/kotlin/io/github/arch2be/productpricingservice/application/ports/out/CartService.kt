package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.application.ports.dto.CartResult
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductQuantity

interface CartService {
    fun loadForProductIdAndQuantity(productId: ProductId, quantity: ProductQuantity): CartResult
}