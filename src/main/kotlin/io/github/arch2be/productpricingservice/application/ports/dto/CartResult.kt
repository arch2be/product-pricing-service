package io.github.arch2be.productpricingservice.application.ports.dto

import io.github.arch2be.productpricingservice.domain.Cart

sealed class CartResult {
    data class Success(val cart: Cart): CartResult()
    data class NotFound(val message: String): CartResult()
}