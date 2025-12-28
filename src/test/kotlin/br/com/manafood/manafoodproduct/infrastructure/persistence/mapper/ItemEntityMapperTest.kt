package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import br.com.manafood.manafoodproduct.testutil.Fixtures
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

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
}

