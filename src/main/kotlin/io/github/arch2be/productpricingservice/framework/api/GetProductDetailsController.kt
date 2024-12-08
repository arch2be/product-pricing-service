package io.github.arch2be.productpricingservice.framework.api

import io.github.arch2be.productpricingservice.application.ports.dto.ProductResult
import io.github.arch2be.productpricingservice.application.usecases.GetProductUseCase
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.framework.api.dto.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/products"])
class GetProductDetailsController(val getProductUseCase: GetProductUseCase) {

    @GetMapping(value = ["/{productId}"])
    fun getProductByUUID(@PathVariable("productId") productId: UUID): ResponseEntity<*> {
        return when(val productResult = getProductUseCase.getProductById(ProductId(productId))) {
            is ProductResult.Success -> ResponseEntity.ok(mapToResponse(productResult.product))
            is ProductResult.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResult.message)
        }
    }

    private fun mapToResponse(product: Product): ProductResponse {
        return ProductResponse(product.id.value, product.name.value, product.price.value)
    }
}