package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.application.usecases.GetProductUseCase
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetProductService(private val productRepository: ProductRepository) : GetProductUseCase {

    override fun getProductById(uuid: UUID): ProductResult {
        val product = productRepository.findProductByUUID(uuid)

        return product?.let {
            ProductResult.Success(it)
        } ?: ProductResult.NotFound("Product with id: $uuid not found.")
    }
}