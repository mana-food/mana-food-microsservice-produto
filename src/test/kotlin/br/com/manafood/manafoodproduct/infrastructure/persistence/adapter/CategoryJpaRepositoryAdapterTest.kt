package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.CategoryJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import java.time.LocalDateTime
import java.util.*

class CategoryJpaRepositoryAdapterTest {

    private val springRepo = mockk<CategoryJpaRepository>()
    private val adapter = CategoryJpaRepositoryAdapter(springRepo)

    @Test
    fun `findById maps entity to domain`() {
        val jpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Cat Y",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        every { springRepo.findByIdAndNotDeleted(jpa.id) } returns Optional.of(jpa)

        val result = adapter.findById(jpa.id)
        assertEquals(jpa.id, result?.id)
    }

    @Test
    fun `findById returns null when category not found`() {
        val id = UUID.randomUUID()
        every { springRepo.findByIdAndNotDeleted(id) } returns Optional.empty()

        val result = adapter.findById(id)
        assertNull(result)
    }

    @Test
    fun `findPaged returns paged categories`() {
        val jpa1 = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category 1",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        val jpa2 = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category 2",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(listOf(jpa1, jpa2), pageable, 2)
        every { springRepo.findPaged(pageable) } returns page

        val result = adapter.findPaged(0, 10)

        assertEquals(2, result.items.size)
        assertEquals(0, result.page)
        assertEquals(10, result.pageSize)
        assertEquals(2, result.totalItems)
    }

    @Test
    fun `findPaged returns empty list when no categories`() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl<CategoryJpaEntity>(emptyList(), pageable, 0)
        every { springRepo.findPaged(pageable) } returns page

        val result = adapter.findPaged(0, 10)

        assertTrue(result.items.isEmpty())
        assertEquals(0, result.totalItems)
    }

    @Test
    fun `save should save and return category`() {
        val category = Category(
            id = UUID.randomUUID(),
            name = "Test Category",
            createdBy = UUID.randomUUID(),
            createdAt = LocalDateTime.now()
        )

        val jpaEntity = CategoryJpaEntity(
            id = category.id,
            name = category.name,
            createdAt = category.createdAt,
            createdBy = category.createdBy
        )

        every { springRepo.save(any()) } returns jpaEntity

        val result = adapter.save(category)

        assertEquals(category.id, result.id)
        assertEquals(category.name, result.name)
        verify(exactly = 1) { springRepo.save(any()) }
    }

    @Test
    fun `deleteById should delete category and return true`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns false

        val result = adapter.deleteById(id)

        assertTrue(result)
        verify(exactly = 1) { springRepo.deleteById(id) }
        verify(exactly = 1) { springRepo.existsByIdAndNotDeleted(id) }
    }

    @Test
    fun `deleteById should return false when category still exists`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns true

        val result = adapter.deleteById(id)

        assertEquals(false, result)
    }

    @Test
    fun `findByIds should return list of categories`() {
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val jpa1 = CategoryJpaEntity(
            id = id1,
            name = "Category 1",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )
        val jpa2 = CategoryJpaEntity(
            id = id2,
            name = "Category 2",
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
    fun `findByIds should return empty list when no categories found`() {
        val ids = listOf(UUID.randomUUID(), UUID.randomUUID())
        every { springRepo.findAllByIdAndNotDeleted(ids) } returns emptyList()

        val result = adapter.findByIds(ids)

        assertTrue(result.isEmpty())
    }
}

