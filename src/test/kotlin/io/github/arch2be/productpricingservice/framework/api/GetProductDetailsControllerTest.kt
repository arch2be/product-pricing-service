package io.github.arch2be.productpricingservice.framework.api

import io.github.arch2be.productpricingservice.DatabaseTestContainer.dbContainer
import io.github.arch2be.productpricingservice.domain.Product
import io.github.arch2be.productpricingservice.domain.ProductId
import io.github.arch2be.productpricingservice.domain.ProductName
import io.github.arch2be.productpricingservice.domain.ProductPrice
import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductEntity
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class GetProductDetailsControllerTest : ShouldSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    lateinit var mockMvc: MockMvc

    init {
        this.beforeAny {
            transaction {
                SchemaUtils.create(ProductEntity)
            }
        }

        this.afterAny {
            transaction {
                ProductEntity.deleteAll()
                SchemaUtils.drop(ProductEntity)
            }
        }

        this.should("should return 400 when uuid is not correct") {
            // Then:
            mockMvc.get("/api/products/23")
                .andExpect {
                    status { isBadRequest() }
                }
        }

        this.should("should return 404 when correct uuid but not exists in DB") {
            // Then:
            mockMvc.get("/api/products/6641e378-f928-4604-8109-9216af8996b9")
                .andExpect {
                    status { isNotFound() }
                }
        }

        this.should("should return 200 with product") {
            // Given:
            val expectedProduct = Product(
                ProductId(UUID.randomUUID()),
                ProductName("name"),
                ProductPrice(BigDecimal("1.0"))
            )

            // When:
            transaction {
                ProductEntity.insert {
                    it[this.id] = expectedProduct.id.value
                    it[this.name] = expectedProduct.name.value
                    it[this.price] = expectedProduct.price.value
                }
            }

            // Then:
            mockMvc.get("/api/products/${expectedProduct.id.value}")
                .andExpect {
                    status { isOk() }
                    jsonPath("\$.id") { value(expectedProduct.id.value.toString()) }
                    jsonPath("\$.name") { value(expectedProduct.name.value) }
                    jsonPath("\$.price") { value(expectedProduct.price.value) }
                }.andReturn()

        }
    }

    companion object {

        @DynamicPropertySource
        @JvmStatic
        fun postgreSQLDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { dbContainer.jdbcUrl }
            registry.add("spring.datasource.username") { dbContainer.username }
            registry.add("spring.datasource.password") { dbContainer.password }
        }
    }
}