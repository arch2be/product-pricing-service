package io.github.arch2be.productpricingservice.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class QuantityDiscountTest: ShouldSpec({

    should("should throw RuntimeException when during the init QuantityDiscount discount is negative") {
        // Throw:
        shouldThrow<RuntimeException> {
            QuantityDiscount(UUID.randomUUID(), UUID.randomUUID(), 1, 5, -1.0)
        }
    }

    should("should throw RuntimeException when during the init QuantityDiscount discount is 0") {
        // Throw:
        shouldThrow<RuntimeException> {
            QuantityDiscount(UUID.randomUUID(), UUID.randomUUID(), 1, 5, 0.0)
        }
    }

    should("should correctly calculate discount") {
        // Given:
        val baseTotalPrice = 120.0
        val expectedDiscount = 12.0
        val quantityDiscount = QuantityDiscount(UUID.randomUUID(), UUID.randomUUID(), 1, 5, 10.0)

        // When:
        val calculatedDiscount = quantityDiscount.calculateDiscount(baseTotalPrice)

        // Then:
        expectedDiscount shouldBe calculatedDiscount
    }
})