package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import br.com.manafood.manafoodproduct.testutil.Fixtures
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ItemEntityMapperTest {

    @Test
    fun `toDomain maps jpa to domain`() {
        val jpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Item A",
            description = "Description Item A",
            category = Fixtures.sampleCategoryJpaEntity(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = LocalDateTime.now(),
            updatedBy = UUID.randomUUID(),
            deleted = false
        )

        val domain = ItemEntityMapper.toDomain(jpa)

        assertEquals(jpa.id, domain.id)
        assertEquals(jpa.name, domain.name)
    }

    @Test
    fun `toDomain should map all fields correctly`() {
        // Given
        val categoryJpa = Fixtures.sampleCategoryJpaEntity()
        val jpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Item Test",
            description = "Description Test",
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = LocalDateTime.now(),
            updatedBy = UUID.randomUUID(),
            deleted = false
        )

        // When
        val domain = ItemEntityMapper.toDomain(jpa)

        // Then
        assertNotNull(domain)
        assertEquals(jpa.id, domain.id)
        assertEquals(jpa.name, domain.name)
        assertEquals(jpa.description, domain.description)
        assertEquals(jpa.category.id, domain.categoryId)
        assertEquals(jpa.createdAt, domain.createdAt)
        assertEquals(jpa.createdBy, domain.createdBy)
        assertEquals(jpa.updatedAt, domain.updatedAt)
        assertEquals(jpa.updatedBy, domain.updatedBy)
        assertEquals(jpa.deleted, domain.deleted)
        assertNotNull(domain.category)
        assertEquals(categoryJpa.name, domain.category.name)
    }

    @Test
    fun `toEntity should map domain to jpa entity`() {
        // Given
        val category = Fixtures.sampleCategory()
        val domain = Item(
            id = UUID.randomUUID(),
            name = "Item Test",
            description = "Description Test",
            categoryId = category.id!!,
            category = category,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = LocalDateTime.now(),
            updatedBy = UUID.randomUUID(),
            deleted = false
        )

        // When
        val entity = ItemEntityMapper.toEntity(domain)

        // Then
        assertNotNull(entity)
        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
        assertEquals(domain.description, entity.description)
        assertEquals(domain.category.id, entity.category.id)
        assertEquals(domain.createdAt, entity.createdAt)
        assertEquals(domain.createdBy, entity.createdBy)
        assertEquals(domain.updatedAt, entity.updatedAt)
        assertEquals(domain.updatedBy, entity.updatedBy)
        assertEquals(domain.deleted, entity.deleted)
    }

    @Test
    fun `toEntity should handle null description`() {
        // Given
        val category = Fixtures.sampleCategory()
        val domain = Item(
            id = UUID.randomUUID(),
            name = "Item Test",
            description = null,
            categoryId = category.id!!,
            category = category,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            deleted = false
        )

        // When
        val entity = ItemEntityMapper.toEntity(domain)

        // Then
        assertNotNull(entity)
        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
        assertEquals(null, entity.description)
    }
}
