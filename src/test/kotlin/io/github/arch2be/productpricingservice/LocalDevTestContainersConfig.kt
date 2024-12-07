package io.github.arch2be.productpricingservice

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer


@TestConfiguration(proxyBeanMethods = false)
class LocalDevTestContainersConfig {

    @Bean
    @ServiceConnection
    fun postgresSQLContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer("postgresql:11.7-alpine")
    }
}