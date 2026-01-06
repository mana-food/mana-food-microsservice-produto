package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.CategoryJpaRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
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
}

