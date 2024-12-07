package io.github.arch2be.productpricingservice.mock

import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Product
import java.util.UUID
import kotlin.collections.HashMap

class ProductRepositoryTestImpl(): ProductRepository {
    private val products = HashMap<UUID, Product>()

    override fun findProductByUUID(uuid: UUID): Product? = products[uuid]

    fun insert(product: Product) {
        products[product.id] = product
    }

    fun deleteAll() {
        products.clear()
    }
}