package br.com.manafood.manafoodproduct.infrastructure.persistence.entity

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class ProductItemJpaEntityTest {

    @Test
    fun `should create ProductItemJpaEntity with all fields`() {
        // Given
        val id = UUID.randomUUID()
        val productId = UUID.randomUUID()
        val itemId = UUID.randomUUID()
        val createdBy = UUID.randomUUID()
        val createdAt = LocalDateTime.now()

        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Category",
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productJpa = ProductJpaEntity(
            id = productId,
            name = "Test Product",
            description = "Description",
            unitPrice = java.math.BigDecimal("10.00"),
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val itemJpa = ItemJpaEntity(
            id = itemId,
            name = "Test Item",
            description = "Item Description",
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        // When
        val productItem = ProductItemJpaEntity(
            id = id,
            product = productJpa,
            item = itemJpa,
            createdAt = createdAt,
            createdBy = createdBy,
            deleted = false
        )

        // Then
        assertNotNull(productItem)
        assertEquals(id, productItem.id)
        assertEquals(productJpa, productItem.product)
        assertEquals(itemJpa, productItem.item)
        assertEquals(createdAt, productItem.createdAt)
        assertEquals(createdBy, productItem.createdBy)
        assertFalse(productItem.deleted)
    }

    @Test
    fun `should create ProductItemJpaEntity with updated fields`() {
        // Given
        val id = UUID.randomUUID()
        val createdBy = UUID.randomUUID()
        val updatedBy = UUID.randomUUID()
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now().plusDays(1)

        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Category",
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            description = "Description",
            unitPrice = java.math.BigDecimal("10.00"),
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val itemJpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Item",
            description = "Item Description",
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        // When
        val productItem = ProductItemJpaEntity(
            id = id,
            product = productJpa,
            item = itemJpa,
            createdAt = createdAt,
            createdBy = createdBy,
            updatedAt = updatedAt,
            updatedBy = updatedBy,
            deleted = false
        )

        // Then
        assertNotNull(productItem)
        assertEquals(updatedAt, productItem.updatedAt)
        assertEquals(updatedBy, productItem.updatedBy)
    }

    @Test
    fun `should allow setting deleted flag`() {
        // Given
        val createdBy = UUID.randomUUID()
        val createdAt = LocalDateTime.now()

        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Category",
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            description = "Description",
            unitPrice = java.math.BigDecimal("10.00"),
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val itemJpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Item",
            description = "Item Description",
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productItem = ProductItemJpaEntity(
            id = UUID.randomUUID(),
            product = productJpa,
            item = itemJpa,
            createdAt = createdAt,
            createdBy = createdBy,
            deleted = false
        )

        // When
        productItem.deleted = true

        // Then
        assert(productItem.deleted)
    }

    @Test
    fun `should allow setting updatedAt and updatedBy`() {
        // Given
        val createdBy = UUID.randomUUID()
        val updatedBy = UUID.randomUUID()
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now().plusHours(1)

        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Category",
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            description = "Description",
            unitPrice = java.math.BigDecimal("10.00"),
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val itemJpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Test Item",
            description = "Item Description",
            category = categoryJpa,
            createdAt = createdAt,
            createdBy = createdBy
        )

        val productItem = ProductItemJpaEntity(
            id = UUID.randomUUID(),
            product = productJpa,
            item = itemJpa,
            createdAt = createdAt,
            createdBy = createdBy,
            deleted = false
        )

        // When
        productItem.updatedAt = updatedAt
        productItem.updatedBy = updatedBy

        // Then
        assertEquals(updatedAt, productItem.updatedAt)
        assertEquals(updatedBy, productItem.updatedBy)
    }
}

