package io.github.arch2be.productpricingservice.domain

import java.math.BigDecimal

data class Cart(
    val product: Product,
    val quantity: ProductQuantity,
    val discounts: List<Discount>,
    val combiningType: CombiningType?) {

    fun calculateTotalPrice(): BigDecimal {
        val totalBasePrice = product.price.value.times(quantity.value.toBigDecimal())
        val mappedDiscounts = discounts.map { it.value }
        val totalDiscount = combiningType?.combine(mappedDiscounts) ?: CombiningType.CHOOSE_THE_BEST.combine(mappedDiscounts)

        return when(totalDiscount) {
            BigDecimal.ZERO -> totalBasePrice
            else -> calculatePriceWithDiscount(totalBasePrice, totalDiscount)
        }
    }

    private fun calculatePriceWithDiscount(totalBasePrice: BigDecimal, totalDiscount: BigDecimal): BigDecimal {
        return totalBasePrice * (BigDecimal.ONE.minus(totalDiscount.divide(BigDecimal(100))))
    }
}

data class ProductQuantity(val value: Int) {
    init {
        require(value > 0) { "ProductQuantity.value must be greater than zero" }
    }
}

data class Discount(val value: BigDecimal) {
    init {
        require(value >= BigDecimal.ZERO) { "Discount.value must be greater or equal zero" }
    }
}
