package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ProductJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ProductJpaRepositoryAdapterTest {

    private val springRepo = mockk<ProductJpaRepository>()
    private val adapter = ProductJpaRepositoryAdapter(springRepo)

    @Test
    fun `findById should return product when found`() {
        // Given
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Product X",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            category = categoryJpa,
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        every { springRepo.findById(productJpa.id) } returns Optional.of(productJpa)

        // When
        val result = adapter.findById(productJpa.id)

        // Then
        assertNotNull(result)
        assertEquals(productJpa.id, result.id)
        assertEquals(productJpa.name, result.name)
        verify(exactly = 1) { springRepo.findById(productJpa.id) }
    }

    @Test
    fun `findById should return null when not found`() {
        // Given
        val id = UUID.randomUUID()
        every { springRepo.findById(id) } returns Optional.empty()

        // When
        val result = adapter.findById(id)

        // Then
        assertNull(result)
        verify(exactly = 1) { springRepo.findById(id) }
    }

    @Test
    fun `findPaged should return paged products`() {
        // Given
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Product X",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            category = categoryJpa,
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val page = PageImpl(listOf(productJpa), PageRequest.of(0, 10), 1)
        every { springRepo.findPaged(PageRequest.of(0, 10)) } returns page

        // When
        val result = adapter.findPaged(0, 10)

        // Then
        assertEquals(1, result.items.size)
        assertEquals(1L, result.totalItems)
        assertEquals(0, result.page)
        assertEquals(10, result.pageSize)
        verify(exactly = 1) { springRepo.findPaged(PageRequest.of(0, 10)) }
    }

    @Test
    fun `save should persist and return product`() {
        // Given
        val category = br.com.manafood.manafoodproduct.domain.model.Category(
            id = UUID.randomUUID(),
            name = "Category",
            createdBy = UUID.randomUUID(),
            deleted = false
        )

        val product = br.com.manafood.manafoodproduct.domain.model.Product(
            id = UUID.randomUUID(),
            name = "Product",
            description = "Desc",
            unitPrice = BigDecimal("100.00"),
            category = category,
            categoryId = category.id!!,
            items = mutableListOf(),
            createdBy = UUID.randomUUID(),
            deleted = false
        )

        val categoryJpa = CategoryJpaEntity(
            id = category.id,
            name = category.name,
            createdAt = LocalDateTime.now(),
            createdBy = category.createdBy
        )

        val savedJpa = ProductJpaEntity(
            id = product.id,
            name = product.name,
            description = product.description,
            unitPrice = product.unitPrice,
            category = categoryJpa,
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = product.createdBy
        )

        every { springRepo.save(any<ProductJpaEntity>()) } returns savedJpa

        // When
        val result = adapter.save(product)

        // Then
        assertNotNull(result)
        assertEquals(product.id, result.id)
        assertEquals(product.name, result.name)
        verify(exactly = 1) { springRepo.save(any<ProductJpaEntity>()) }
    }
}

