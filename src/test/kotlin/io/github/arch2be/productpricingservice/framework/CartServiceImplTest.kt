package io.github.arch2be.productpricingservice.framework

import io.github.arch2be.productpricingservice.application.ports.dto.CartResult
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.*
import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountRepository
import io.github.arch2be.productpricingservice.framework.orm.ProductDiscountConfigurationRepository
import io.github.arch2be.productpricingservice.framework.orm.QuantityDiscountRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*

class CartServiceImplTest: ShouldSpec({
    val productDiscountConfigurationMockRepository = mockk<ProductDiscountConfigurationRepository>()
    val quantityDiscountMockRepository = mockk<QuantityDiscountRepository>()
    val flatDiscountMockRepository = mockk<FlatDiscountRepository>()
    val productMockRepository = mockk<ProductRepository>()

    val sut = CartServiceImpl(
        productDiscountConfigurationMockRepository,
        quantityDiscountMockRepository,
        flatDiscountMockRepository,
        productMockRepository)


    should("should return CartResult.NotFound with message when product not found") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val quantity = ProductQuantity(1)
        val expectedCartResult = CartResult.NotFound("product with id: ${productId.value} not found")

        // When:
        every { productMockRepository.findById(any()) } returns null

        // Then:
        val actualProductResult = sut.loadForProductIdAndQuantity(productId, quantity)
        actualProductResult shouldBe expectedCartResult
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }

    should("should return CartResult.Success with cart with product, discounts and combining type") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val quantity = ProductQuantity(1)
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(5)))
        val flatDiscount = BigDecimal(10)
        val quantityDiscount = BigDecimal(15)
        val combineType = CombiningType.CHOOSE_THE_BEST

        val expectedCartResult = CartResult
            .Success(Cart(product, quantity, listOf(Discount(quantityDiscount), Discount(flatDiscount)), combineType))

        // When:
        every { productDiscountConfigurationMockRepository.findCombineTypeByProductId(any()) } returns combineType
        every { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(any(), any()) } returns quantityDiscount
        every { flatDiscountMockRepository.findDiscountByProductId(any()) } returns flatDiscount
        every { productMockRepository.findById(any()) } returns product

        // Then:
        val actualProductResult = sut.loadForProductIdAndQuantity(productId, quantity)
        actualProductResult shouldBe expectedCartResult
        verify(exactly = 1) { productDiscountConfigurationMockRepository.findCombineTypeByProductId(productId.value) }
        verify(exactly = 1) { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(productId.value, quantity.value) }
        verify(exactly = 1) { flatDiscountMockRepository.findDiscountByProductId(productId.value) }
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }

    should("should return CartResult.Success with cart with product, combining type without discounts") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val quantity = ProductQuantity(1)
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(5)))
        val flatDiscount = BigDecimal.ZERO
        val quantityDiscount = BigDecimal.ZERO
        val combineType = CombiningType.CHOOSE_THE_BEST

        val expectedCartResult = CartResult
            .Success(Cart(product, quantity, listOf(Discount(quantityDiscount), Discount(flatDiscount)), combineType))

        // When:
        every { productDiscountConfigurationMockRepository.findCombineTypeByProductId(any()) } returns combineType
        every { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(any(), any()) } returns null
        every { flatDiscountMockRepository.findDiscountByProductId(any()) } returns null
        every { productMockRepository.findById(any()) } returns product

        // Then:
        val actualProductResult = sut.loadForProductIdAndQuantity(productId, quantity)
        actualProductResult shouldBe expectedCartResult
        verify(exactly = 1) { productDiscountConfigurationMockRepository.findCombineTypeByProductId(productId.value) }
        verify(exactly = 1) { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(productId.value, quantity.value) }
        verify(exactly = 1) { flatDiscountMockRepository.findDiscountByProductId(productId.value) }
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }

    should("should return CartResult.Success with cart with product, discounts and without combining type") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val quantity = ProductQuantity(1)
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(5)))
        val flatDiscount = BigDecimal(10)
        val quantityDiscount = BigDecimal(15)

        val expectedCartResult = CartResult
            .Success(Cart(product, quantity, listOf(Discount(quantityDiscount), Discount(flatDiscount)), null))

        // When:
        every { productDiscountConfigurationMockRepository.findCombineTypeByProductId(any()) } returns null
        every { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(any(), any()) } returns quantityDiscount
        every { flatDiscountMockRepository.findDiscountByProductId(any()) } returns flatDiscount
        every { productMockRepository.findById(any()) } returns product

        // Then:
        val actualProductResult = sut.loadForProductIdAndQuantity(productId, quantity)
        actualProductResult shouldBe expectedCartResult
        verify(exactly = 1) { productDiscountConfigurationMockRepository.findCombineTypeByProductId(productId.value) }
        verify(exactly = 1) { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(productId.value, quantity.value) }
        verify(exactly = 1) { flatDiscountMockRepository.findDiscountByProductId(productId.value) }
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }

    should("should return CartResult.Success with cart with product, without discounts and combining type") {
        // Given:
        val productId = ProductId(UUID.randomUUID())
        val quantity = ProductQuantity(1)
        val product = Product(productId, ProductName("name"), ProductPrice(BigDecimal(5)))
        val flatDiscount = BigDecimal.ZERO
        val quantityDiscount = BigDecimal.ZERO

        val expectedCartResult = CartResult
            .Success(Cart(product, quantity, listOf(Discount(quantityDiscount), Discount(flatDiscount)), null))

        // When:
        every { productDiscountConfigurationMockRepository.findCombineTypeByProductId(any()) } returns null
        every { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(any(), any()) } returns null
        every { flatDiscountMockRepository.findDiscountByProductId(any()) } returns null
        every { productMockRepository.findById(any()) } returns product

        // Then:
        val actualProductResult = sut.loadForProductIdAndQuantity(productId, quantity)
        actualProductResult shouldBe expectedCartResult
        verify(exactly = 1) { productDiscountConfigurationMockRepository.findCombineTypeByProductId(productId.value) }
        verify(exactly = 1) { quantityDiscountMockRepository.findDiscountByProductIdAndQuantity(productId.value, quantity.value) }
        verify(exactly = 1) { flatDiscountMockRepository.findDiscountByProductId(productId.value) }
        verify(exactly = 1) { productMockRepository.findById(productId) }
    }

})