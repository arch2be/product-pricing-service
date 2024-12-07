package io.github.arch2be.productpricingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductPricingServiceApplication

fun main(args: Array<String>) {
	runApplication<ProductPricingServiceApplication>(*args)
}
