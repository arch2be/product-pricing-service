package io.github.arch2be.productpricingservice.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import java.math.BigDecimal
import java.util.*

class ProductTest: ShouldSpec({

    should("should throw IllegalArgumentException when during the init Product price is negative") {
        // Throw:
        shouldThrow<IllegalArgumentException> {
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal(-1.0)))
        }
    }

    should("should throw IllegalArgumentException when during the init Product price is 0") {
        // Throw:
        shouldThrow<IllegalArgumentException> {
            Product(ProductId(UUID.randomUUID()), ProductName("name"), ProductPrice(BigDecimal.ZERO))
        }
    }
})