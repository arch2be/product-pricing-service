package io.github.arch2be.productpricingservice.application.ports.dto

import io.github.arch2be.productpricingservice.domain.Product

sealed class ProductResult {
    data class Success(val product: Product): ProductResult()
    data class NotFound(val message: String): ProductResult()
}