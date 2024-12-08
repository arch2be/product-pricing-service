package io.github.arch2be.productpricingservice

import org.springframework.boot.runApplication


class LocalDevApplication

fun main(args: Array<String>) {
    runApplication<ProductPricingServiceApplication>(*args) {
        DatabaseTestContainer::class
    }
}