package io.github.arch2be.productpricingservice.domain

enum class CombiningType {
    CUMULATIVE {
        override fun combine(discounts: List<Double>): Double = discounts.sum()
    },
    CHOOSE_THE_BEST {
        override fun combine(discounts: List<Double>): Double = discounts.max()
    };

    abstract fun combine(discounts: List<Double>): Double
}