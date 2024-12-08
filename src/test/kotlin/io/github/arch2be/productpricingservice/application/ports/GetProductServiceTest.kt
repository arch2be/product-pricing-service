package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductName
import io.github.arch2be.productpricingservice.domain.ProductPrice
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*

class GetProductServiceTest: ShouldSpec({

    val productMockRepository = mockk<ProductRepository>()
    val sut = GetProductService(productMockRepository)

    should("should return ProductResult.Success with correct Product when pass uuid for existing Product") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(1.0)))
        val expectedProductResult = ProductResult.Success(product)

        // When:
        every { productMockRepository.findById(any()) } returns product

        // Then:
        val actualProductResult = sut.getProductById(productId)
        actualProductResult shouldBe expectedProductResult
        verify(exactly = 1) { productMockRepository.findById(productId) }

    }

    should("should return ProductResult.NotFound with correct message when pass uuid for not existing Product") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val expectedProductResult = ProductResult.NotFound("Product with id: ${productId.value} not found.")

        // When:
        every { productMockRepository.findById(any()) } returns null

        // Then:
        val actualProductResult = sut.getProductById(productId)
        actualProductResult shouldBe expectedProductResult
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }
})