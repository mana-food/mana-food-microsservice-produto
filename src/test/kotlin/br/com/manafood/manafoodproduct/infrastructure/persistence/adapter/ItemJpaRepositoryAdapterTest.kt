package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

class ItemJpaRepositoryAdapterTest {

    private val springRepo = mockk<ItemJpaRepository>()
    private val adapter = ItemJpaRepositoryAdapter(springRepo)

    @Test
    fun `findById maps entity to domain`() {
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val jpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Item X",
            description = "Description",
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        every { springRepo.findByIdAndNotDeleted(jpa.id) } returns Optional.of(jpa)

        val result = adapter.findById(jpa.id)
        assertEquals(jpa.id, result?.id)
    }

    @Test
    fun `findById returns null when item not found`() {
        val id = UUID.randomUUID()
        every { springRepo.findByIdAndNotDeleted(id) } returns Optional.empty()

        val result = adapter.findById(id)
        assertNull(result)
    }

    @Test
    fun `findAll paged maps correctly`() {
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val jpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Item X",
            description = "Description",
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        val page = PageImpl(listOf(jpa))

        every { springRepo.findPaged(PageRequest.of(0, 10)) } returns page

        val result = adapter.findPaged(0, 10)
        assertEquals(1, result.items.size)
    }

    @Test
    fun `findPaged returns empty list when no items`() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl<ItemJpaEntity>(emptyList(), pageable, 0)
        every { springRepo.findPaged(pageable) } returns page

        val result = adapter.findPaged(0, 10)

        assertTrue(result.items.isEmpty())
        assertEquals(0, result.totalItems)
    }

    @Test
    fun `deleteById should delete item and return true`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns false

        val result = adapter.deleteById(id)

        assertTrue(result)
        verify(exactly = 1) { springRepo.deleteById(id) }
    }

    @Test
    fun `deleteById should return false when item still exists`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns true

        val result = adapter.deleteById(id)

        assertEquals(false, result)
    }

    @Test
    fun `findByIds should return list of items`() {
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val jpa1 = ItemJpaEntity(
            id = id1,
            name = "Item 1",
            description = "Description 1",
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        val jpa2 = ItemJpaEntity(
            id = id2,
            name = "Item 2",
            description = "Description 2",
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        every { springRepo.findAllByIdAndNotDeleted(listOf(id1, id2)) } returns listOf(jpa1, jpa2)

        val result = adapter.findByIds(listOf(id1, id2))

        assertEquals(2, result.size)
        assertEquals(id1, result[0].id)
        assertEquals(id2, result[1].id)
    }

    @Test
    fun `findByIds should return empty list when no items found`() {
        val ids = listOf(UUID.randomUUID(), UUID.randomUUID())
        every { springRepo.findAllByIdAndNotDeleted(ids) } returns emptyList()

        val result = adapter.findByIds(ids)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `findById with item without description`() {
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val jpa = ItemJpaEntity(
            id = UUID.randomUUID(),
            name = "Item X",
            description = null,
            category = categoryJpa,
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        every { springRepo.findByIdAndNotDeleted(jpa.id) } returns Optional.of(jpa)

        val result = adapter.findById(jpa.id)

        assertEquals(jpa.id, result?.id)
        assertNull(result?.description)
    }
}

