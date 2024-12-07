package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.mock.FlatDiscountRepositoryTestImpl
import io.github.arch2be.productpricingservice.mock.ProductDiscountConfigurationRepositoryTestImpl
import io.github.arch2be.productpricingservice.mock.ProductRepositoryTestImpl
import io.github.arch2be.productpricingservice.mock.QuantityDiscountRepositoryTestImpl
import io.kotest.core.spec.style.ShouldSpec

class CalculateTotalPriceForProductUseCaseServiceTest: ShouldSpec({

    val productDiscountConfigurationTestRepository = ProductDiscountConfigurationRepositoryTestImpl()
    val quantityDiscountTestRepository = QuantityDiscountRepositoryTestImpl()
    val flatDiscountTestRepository = FlatDiscountRepositoryTestImpl()
    val productTestRepository = ProductRepositoryTestImpl()

    val sut = CalculateTotalPriceForProductUseCaseService(
        productDiscountConfigurationTestRepository,
        quantityDiscountTestRepository,
        flatDiscountTestRepository,
        productTestRepository
    )

    should("should return ProductResult.Success with correct Product when pass uuid for existing Product") {

    }
})