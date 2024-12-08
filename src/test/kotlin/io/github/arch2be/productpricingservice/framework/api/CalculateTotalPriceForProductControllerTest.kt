package io.github.arch2be.productpricingservice.framework.api

import io.github.arch2be.productpricingservice.DatabaseTestContainer.dbContainer
import io.github.arch2be.productpricingservice.domain.*
import io.github.arch2be.productpricingservice.framework.orm.FlatDiscountEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductDiscountConfigurationEntity
import io.github.arch2be.productpricingservice.framework.orm.ProductEntity
import io.github.arch2be.productpricingservice.framework.orm.QuantityDiscountEntity
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
class CalculateTotalPriceForProductControllerTest : ShouldSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    lateinit var mockMvc: MockMvc

    init {
        this.beforeAny {
            transaction {
                SchemaUtils.create(
                    ProductEntity,
                    QuantityDiscountEntity,
                    FlatDiscountEntity,
                    ProductDiscountConfigurationEntity
                )
            }
        }

        this.afterAny {
            transaction {
                ProductDiscountConfigurationEntity.deleteAll()
                QuantityDiscountEntity.deleteAll()
                FlatDiscountEntity.deleteAll()
                ProductEntity.deleteAll()
                SchemaUtils.drop(
                    ProductEntity,
                    QuantityDiscountEntity,
                    FlatDiscountEntity,
                    ProductDiscountConfigurationEntity
                )
            }
        }

        this.should("should return 400 when uuid is not correct") {
            // Then:
            mockMvc.get("/calculate?productId=21&quantity=1")
                .andExpect {
                    status { isBadRequest() }
                }
        }

        this.should("should return 400 when quantity empty") {
            // Then:
            mockMvc.get("/calculate?productId=21&quantity=")
                .andExpect {
                    status { isBadRequest() }
                }
        }

        this.should("should return 404 when product not found") {
            // Then:
            mockMvc.get("/calculate?productId=6641e378-f928-4604-8109-9216af8996b9&quantity=1")
                .andExpect {
                    status { isNotFound() }
                }
        }

        this.should("should return total price without any discount when discount not found") {
            // Given:
            val quantity = 2
            val product = Product(
                ProductId(UUID.randomUUID()),
                ProductName("name"),
                ProductPrice(BigDecimal("10.0"))
            )
            val expectedPrice = BigDecimal("20.0")

            // When:
            transaction {
                ProductEntity.insert {
                    it[this.id] = product.id.value
                    it[this.name] = product.name.value
                    it[this.price] = product.price.value
                }
            }

            // Then:
            mockMvc.get("/calculate?productId=${product.id.value}&quantity=$quantity")
                .andExpect {
                    status { isOk() }
                    jsonPath("\$.price") { value(expectedPrice) }
                }
        }

        this.should("should return total price with the best discount when combining type is not defined") {
            // Given:
            val quantity = 2
            val product = Product(
                ProductId(UUID.randomUUID()),
                ProductName("name"),
                ProductPrice(BigDecimal("10.0"))
            )
            val expectedPrice = BigDecimal("16.0")

            // When:
            transaction {
                ProductEntity.insert {
                    it[this.id] = product.id.value
                    it[this.name] = product.name.value
                    it[this.price] = product.price.value
                }

                FlatDiscountEntity.insert {
                    it[this.id] = UUID.randomUUID()
                    it[this.productId] = product.id.value
                    it[this.discount] = 10.0
                }

                QuantityDiscountEntity.insert {
                    it[this.id] = UUID.randomUUID()
                    it[this.productId] = product.id.value
                    it[this.min] = 1
                    it[this.max] = 20
                    it[this.discount] = 20.0
                }
            }

            // Then:
            mockMvc.get("/calculate?productId=${product.id.value}&quantity=$quantity")
                .andExpect {
                    status { isOk() }
                    jsonPath("\$.price") { value(expectedPrice) }
                }
        }

        this.should("should return total price with the sum discount") {
            // Given:
            val quantity = 2
            val product = Product(
                ProductId(UUID.randomUUID()),
                ProductName("name"),
                ProductPrice(BigDecimal("10.0"))
            )
            val expectedPrice = BigDecimal("14.0")

            // When:
            transaction {
                ProductEntity.insert {
                    it[this.id] = product.id.value
                    it[this.name] = product.name.value
                    it[this.price] = product.price.value
                }

                FlatDiscountEntity.insert {
                    it[this.id] = UUID.randomUUID()
                    it[this.productId] = product.id.value
                    it[this.discount] = 10.0
                }

                QuantityDiscountEntity.insert {
                    it[this.id] = UUID.randomUUID()
                    it[this.productId] = product.id.value
                    it[this.min] = 1
                    it[this.max] = 20
                    it[this.discount] = 20.0
                }

                ProductDiscountConfigurationEntity.insert {
                    it[this.id] = UUID.randomUUID()
                    it[this.productId] = product.id.value
                    it[this.combiningType] = CombiningType.CUMULATIVE
                }
            }

            // Then:
            mockMvc.get("/calculate?productId=${product.id.value}&quantity=$quantity")
                .andExpect {
                    status { isOk() }
                    jsonPath("\$.price") { value(expectedPrice) }
                }
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