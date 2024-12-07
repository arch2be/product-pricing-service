package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductName
import io.github.arch2be.productpricingservice.domain.ProductPrice
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl: ProductRepository {

    override fun findById(productId: ProductId): Product? {
        return transaction {
            ProductEntity
                .selectAll()
                .where { ProductEntity.id eq productId.value }
                .firstOrNull()
                ?.let {
                    Product(
                        ProductId(it[ProductEntity.id].value),
                        ProductName(it[ProductEntity.name]),
                        ProductPrice(it[ProductEntity.price])
                    )
                }
        }
    }
}