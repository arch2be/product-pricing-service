package io.github.arch2be.productpricingservice.framework.api

import io.github.arch2be.productpricingservice.application.ports.dto.CalculationResult
import io.github.arch2be.productpricingservice.application.usecases.CalculateTotalPriceForProductUseCase
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductQuantity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/calculate"])
class CalculateTotalPriceForProductController(private val calculateTotalPriceForProductUseCase: CalculateTotalPriceForProductUseCase) {

    @GetMapping
    fun calculateDiscountForProductWithQuantity(@RequestParam(name = "productId") productId: UUID,
                                                @RequestParam(name = "quantity") quantity: Int): ResponseEntity<*> {
        return when(val productResult = calculateTotalPriceForProductUseCase.calculateTotalPriceBasedOnProductAndQuantity(ProductId(productId), ProductQuantity(quantity))) {
            is CalculationResult.Success -> ResponseEntity.ok(productResult)
            is CalculationResult.NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResult.message)
        }
    }
}