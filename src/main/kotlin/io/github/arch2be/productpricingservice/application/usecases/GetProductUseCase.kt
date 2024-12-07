package io.github.arch2be.productpricingservice.application.usecases

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import java.util.*


interface GetProductUseCase {
    fun getProductById(uuid: UUID): ProductResult
}