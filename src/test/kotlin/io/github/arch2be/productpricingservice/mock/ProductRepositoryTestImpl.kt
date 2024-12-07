package io.github.arch2be.productpricingservice.mock

import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId

class ProductRepositoryTestImpl: ProductRepository {
    private val products = HashMap<ProductId, Product>()

    override fun findById(productId: ProductId): Product? = products[productId]

    fun insert(product: Product) {
        products[product.id] = product
    }

    fun deleteAll() {
        products.clear()
    }
}