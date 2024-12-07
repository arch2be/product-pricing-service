package io.github.arch2be.productpricingservice.domain

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class CombiningTypeTest: ShouldSpec({

    should("should return the highest discount when CombineType is CHOOSE_THE_BEST") {
        // Given:
        val expectedDiscount = 15.0
        val discounts = listOf(13.0, expectedDiscount)

        // When:
        val selectedDiscount = CombiningType.CHOOSE_THE_BEST.combine(discounts)

        // Then:
        selectedDiscount shouldBe expectedDiscount
    }

    should("should return the sum of the passed discounts when CombineType is CUMULATIVE") {
        // Given:
        val expectedDiscount = 28.0
        val discounts = listOf(13.0, 15.0)

        // When:
        val selectedDiscount = CombiningType.CUMULATIVE.combine(discounts)

        // Then:
        selectedDiscount shouldBe expectedDiscount
    }
})