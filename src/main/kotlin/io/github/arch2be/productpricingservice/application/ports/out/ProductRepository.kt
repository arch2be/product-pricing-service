package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId

interface ProductRepository {
    fun findById(productId: ProductId): Product?
}