package io.github.arch2be.productpricingservice

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.testcontainers.containers.PostgreSQLContainer

object DatabaseTestContainer {
    val dbContainer = PostgreSQLContainer("postgres:latest")

    init {
        dbContainer.start()
    }

    val dataSource = HikariConfig()
        .apply {
            jdbcUrl = dbContainer.jdbcUrl
            username = dbContainer.username
            password = dbContainer.password
            driverClassName = dbContainer.driverClassName
        }.let {
            HikariDataSource(it)
        }
}