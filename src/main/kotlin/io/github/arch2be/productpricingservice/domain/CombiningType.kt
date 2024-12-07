package io.github.arch2be.productpricingservice.domain

import java.math.BigDecimal

enum class CombiningType {
    CUMULATIVE {
        override fun combine(discounts: List<BigDecimal>): BigDecimal = discounts.sumOf { it.plus() }
    },
    CHOOSE_THE_BEST {
        override fun combine(discounts: List<BigDecimal>): BigDecimal = discounts.max()
    };

    abstract fun combine(discounts: List<BigDecimal>): BigDecimal
}