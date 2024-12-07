package io.github.arch2be.productpricingservice.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class FlatDiscountTest: ShouldSpec({

    should("should throw RuntimeException when during the init FlatDiscount discount is negative") {
        // Throw:
        shouldThrow<RuntimeException> {
            FlatDiscount(UUID.randomUUID(), UUID.randomUUID(), -1.0)
        }
    }

    should("should throw RuntimeException when during the init FlatDiscount discount is 0") {
        // Throw:
        shouldThrow<RuntimeException> {
            FlatDiscount(UUID.randomUUID(), UUID.randomUUID(), 0.0)
        }
    }

    should("should correctly calculate discount") {
        // Given:
        val baseTotalPrice = 120.0
        val expectedDiscount = 12.0
        val flatDiscount = FlatDiscount(UUID.randomUUID(), UUID.randomUUID(), 10.0)

        // When:
        val calculatedDiscount = flatDiscount.calculateDiscount(baseTotalPrice)

        // Then:
        expectedDiscount shouldBe calculatedDiscount
    }
})