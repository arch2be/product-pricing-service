package io.github.arch2be.productpricingservice.application.ports

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.application.ports.out.ProductRepository
import io.github.arch2be.productpricingservice.application.usecases.GetProductUseCase
import io.github.arch2be.productpricingservice.domain.ProductId
import org.springframework.stereotype.Service

@Service
class GetProductService(private val productRepository: ProductRepository) : GetProductUseCase {

    override fun getProductById(productId: ProductId): ProductResult {
        val product = productRepository.findById(productId)
            ?: return ProductResult.NotFound("Product with id: ${productId.value} not found.")

        return ProductResult.Success(product)
    }
}