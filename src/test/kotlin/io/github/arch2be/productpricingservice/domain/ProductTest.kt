package io.github.arch2be.productpricingservice.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import java.util.UUID

class ProductTest: ShouldSpec({

    should("should throw RuntimeException when during the init Product price is negative") {
        // Throw:
        shouldThrow<RuntimeException> {
            Product(UUID.randomUUID(), "name", -1.0)
        }
    }

    should("should throw RuntimeException when during the init Product price is 0") {
        // Throw:
        shouldThrow<RuntimeException> {
            Product(UUID.randomUUID(), "name", 0.0)
        }
    }
})