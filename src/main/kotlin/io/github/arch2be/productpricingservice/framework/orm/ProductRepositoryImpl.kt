package io.github.arch2be.productpricingservice.framework.orm

import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.domain.Product
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductRepositoryImpl: ProductRepository {

    override fun findById(uuid: UUID): Product? {
        return transaction {
            ProductEntity
                .selectAll()
                .where { ProductEntity.id eq uuid }
                .firstOrNull()
                ?.let { Product(it[ProductEntity.id].value, it[ProductEntity.name], it[ProductEntity.price]) }
        }
    }
}