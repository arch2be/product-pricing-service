package io.github.arch2be.productpricingservice.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.util.*

class CartTest: ShouldSpec({

    should("should throw IllegalArgumentException when during the init Cart quantity is negative") {
        // Throw:
        shouldThrow<IllegalArgumentException> {
            Cart(
                Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal.ONE)),
                ProductQuantity(-1),
                listOf(Discount(BigDecimal.ONE)),
                null)
        }
    }

    should("should throw IllegalArgumentException when during the init Cart quantity is 0") {
        // Throw:
        shouldThrow<IllegalArgumentException> {
            Cart(
                Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal.ONE)),
                ProductQuantity(0),
                listOf(Discount(BigDecimal.ONE)),
                null)
        }
    }

    should("should calculateTotalPrice with the best discount when two discount are passed and combiningType is null(default mechanism)") {
        // Given:
        val expectedTotalPrice = BigDecimal("160.0")
        val cart = Cart(
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal(100))),
            ProductQuantity(2),
            listOf(Discount(BigDecimal(10)), Discount(BigDecimal(20))),
            null)

        // Then:
        val calculatedTotalPrice = cart.calculateTotalPrice()
        calculatedTotalPrice shouldBe expectedTotalPrice
    }

    should("should calculateTotalPrice with the best discount when two discount are passed and combiningType is CHOOSE_THE_BEST") {
        // Given:
        val expectedTotalPrice = BigDecimal("160.0")
        val cart = Cart(
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal(100))),
            ProductQuantity(2),
            listOf(Discount(BigDecimal(10)), Discount(BigDecimal(20))),
            CombiningType.CHOOSE_THE_BEST)

        // When:
        val calculatedTotalPrice = cart.calculateTotalPrice()
        calculatedTotalPrice shouldBe expectedTotalPrice
    }

    should("should calculateTotalPrice with sum discounts when two discount are passed and combiningType is CUMULATIVE") {
        // Given:
        val expectedTotalPrice = BigDecimal("140.0")
        val cart = Cart(
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal(100))),
            ProductQuantity(2),
            listOf(Discount(BigDecimal(10)), Discount(BigDecimal(20))),
            CombiningType.CUMULATIVE)

        // Then:
        val calculatedTotalPrice = cart.calculateTotalPrice()
        calculatedTotalPrice shouldBe expectedTotalPrice
    }

    should("should return not discounted total price when passed discounts are zero") {
        // Given:
        val expectedTotalPrice = BigDecimal("200")
        val cart = Cart(
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal(100))),
            ProductQuantity(2),
            listOf(Discount(BigDecimal.ZERO), Discount(BigDecimal.ZERO)),
            CombiningType.CUMULATIVE)

        // Then:
        val calculatedTotalPrice = cart.calculateTotalPrice()
        calculatedTotalPrice shouldBe expectedTotalPrice
    }
})