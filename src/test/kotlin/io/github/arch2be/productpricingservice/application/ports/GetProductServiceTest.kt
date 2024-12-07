package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductName
import io.github.arch2be.productpricingservice.domain.ProductPrice
import io.github.arch2be.productpricingservice.mock.ProductRepositoryTestImpl
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.util.UUID

class GetProductServiceTest: ShouldSpec({

    val productTestRepository = ProductRepositoryTestImpl()
    val sut = GetProductService(productTestRepository)

    should("should return ProductResult.Success with correct Product when pass uuid for existing Product") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(1.0)))
        val expectedProductResult = ProductResult.Success(product)
        productTestRepository.insert(product)

        // When:
        val actualProductResult = sut.getProductById(productId)

        // Then:
        actualProductResult shouldBe expectedProductResult

        // Cleanup:
        productTestRepository.deleteAll()
    }

    should("should return ProductResult.NotFound with correct message when pass uuid for not existing Product") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val expectedProductResult = ProductResult.NotFound("Product with id: ${productId.value} not found.")

        // When:
        val actualProductResult = sut.getProductById(productId)

        // Then:
        actualProductResult shouldBe expectedProductResult

        // Cleanup:
        productTestRepository.deleteAll()
    }
})