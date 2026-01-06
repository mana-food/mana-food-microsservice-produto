package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
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
}

