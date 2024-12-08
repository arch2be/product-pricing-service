package io.github.arch2be.productpricingservice.domain

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class CombiningTypeTest: ShouldSpec({

    should("should return the highest discount when CombineType is CHOOSE_THE_BEST") {
        // Given:
        val expectedDiscount = BigDecimal(15.0)
        val discounts = listOf(BigDecimal(13.0), expectedDiscount)

        // Then:
        val selectedDiscount = CombiningType.CHOOSE_THE_BEST.combine(discounts)
        selectedDiscount shouldBe expectedDiscount
    }

    should("should return the sum of the passed discounts when CombineType is CUMULATIVE") {
        // Given:
        val expectedDiscount = BigDecimal(28.0)
        val discounts = listOf(BigDecimal(13.0), BigDecimal(15.0))

        // Then:
        val selectedDiscount = CombiningType.CUMULATIVE.combine(discounts)
        selectedDiscount shouldBe expectedDiscount
    }
})