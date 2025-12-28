package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import br.com.manafood.manafoodproduct.testutil.Fixtures
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProductEntityMapperTest {

    @Test
    fun `toDomain should map jpa entity to domain`() {
        val jpa = Fixtures.sampleProductJpaEntity()

        val domain = ProductEntityMapper.toDomain(
            ProductJpaEntity(
                id = jpa.id,
                name = jpa.name,
                description = jpa.description,
                unitPrice = jpa.unitPrice,
                category = CategoryJpaEntity(
                    id = UUID.randomUUID(),
                    name = "Categoria A",
                    createdAt = LocalDateTime.now(),
                    createdBy = UUID.randomUUID(),
                    updatedAt = LocalDateTime.now(),
                    updatedBy = UUID.randomUUID(),
                    deleted = false
                ),
                createdAt = jpa.createdAt,
                updatedAt = jpa.updatedAt,
                createdBy = jpa.createdBy,
                updatedBy = jpa.updatedBy,
                deleted = jpa.deleted
            )
        )

        assertNotNull(domain)
        assertEquals(jpa.id, domain.id)
        assertEquals(jpa.name, domain.name)
    }

    @Test
    fun `toEntity should map domain to jpa entity`() {
        val domain = Fixtures.sampleProduct()

        val entity = ProductEntityMapper.toEntity(domain)

        assertNotNull(entity)
        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
    }
}

