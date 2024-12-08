package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import io.github.arch2be.productpricingservice.application.ports.dto.CartResult
import io.github.arch2be.productpricingservice.application.ports.out.CartService
import io.github.arch2be.productpricingservice.domain.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*

class CalculateTotalPriceForProductUseCaseServiceTest: ShouldSpec({
    val cartMockService = mockk<CartService>()
    val sut = CalculateTotalPriceForProductUseCaseService(cartMockService)

    should("should return CartResult.Success with correct discounted total price when CartService return CartResult.Success") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val productQuantity = ProductQuantity(1)
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(100)))

        val cart = Cart(product, productQuantity, listOf(Discount(BigDecimal(10))), null)
        val expectedProductResult = CalculationResult.Success(BigDecimal("90.0"))

        // When:
        every { cartMockService.loadForProductIdAndQuantity(any(), any()) } returns CartResult.Success(cart)

        // Then:
        val actualProductResult = sut.calculateTotalPriceBasedOnProductAndQuantity(productId, productQuantity)
        actualProductResult shouldBe expectedProductResult
        verify(exactly = 1) { cartMockService.loadForProductIdAndQuantity(productId, productQuantity) }
    }

    should("should return CartResult.NotFound with correct message when CartService return CartResult.NotFound") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val productQuantity = ProductQuantity(1)
        val notFoundMessage = "product with id: ${productId.value} not found"
        val expectedProductResult = CalculationResult.NotFound(notFoundMessage)

        // When:
        every { cartMockService.loadForProductIdAndQuantity(any(), any()) } returns CartResult.NotFound(notFoundMessage)

        // Then:
        val actualProductResult = sut.calculateTotalPriceBasedOnProductAndQuantity(productId, productQuantity)
        actualProductResult shouldBe expectedProductResult
        verify(exactly = 1) { cartMockService.loadForProductIdAndQuantity(productId, productQuantity) }
    }
})