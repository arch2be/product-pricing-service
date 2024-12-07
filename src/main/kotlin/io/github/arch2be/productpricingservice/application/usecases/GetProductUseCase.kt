package io.github.arch2be.productpricingservice.application.usecases

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.domain.ProductId


interface GetProductUseCase {
    fun getProductById(productId: ProductId): ProductResult
}