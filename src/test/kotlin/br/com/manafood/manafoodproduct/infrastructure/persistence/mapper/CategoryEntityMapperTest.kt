package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryEntityMapperTest {

    @Test
    fun `toDomain maps category entity`() {
        val jpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Categoria X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = LocalDateTime.now(),
            updatedBy = UUID.randomUUID(),
            deleted = false
        )

        val domain = CategoryEntityMapper.toDomain(jpa)
        assertEquals(jpa.id, domain.id)
        assertEquals(jpa.name, domain.name)
    }
}

