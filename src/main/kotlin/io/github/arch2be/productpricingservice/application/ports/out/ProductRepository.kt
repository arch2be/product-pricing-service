package io.github.arch2be.productpricingservice.application.ports.out

import io.github.arch2be.productpricingservice.domain.Product
import java.util.UUID

interface ProductRepository {
    fun findById(uuid: UUID): Product?
}