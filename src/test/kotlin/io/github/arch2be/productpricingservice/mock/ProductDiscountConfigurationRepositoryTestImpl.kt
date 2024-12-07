package io.github.arch2be.productpricingservice.mock

import io.github.arch2be.productpricingservice.application.ports.out.ProductDiscountConfigurationRepository
import io.github.arch2be.productpricingservice.domain.ProductDiscountConfiguration
import java.util.*

class ProductDiscountConfigurationRepositoryTestImpl: ProductDiscountConfigurationRepository {
    private val productDiscountConfigurations = HashMap<UUID, ProductDiscountConfiguration>()

    override fun findByProductId(productId: UUID): ProductDiscountConfiguration? = productDiscountConfigurations[productId]

    fun insert(productDiscountConfiguration: ProductDiscountConfiguration) {
        productDiscountConfigurations[productDiscountConfiguration.productId] = productDiscountConfiguration
    }

    fun deleteAll() {
        productDiscountConfigurations.clear()
    }
}