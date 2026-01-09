package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.mapper.ProductEntityMapper
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
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
import kotlin.test.assertTrue

class ProductJpaRepositoryAdapterTest {

    private val springRepo = mockk<ProductJpaRepository>()
    private val productEntityMapper = mockk<ProductEntityMapper>()
    private val itemJpaRepository = mockk<ItemJpaRepository>()
    private val adapter = ProductJpaRepositoryAdapter(springRepo, productEntityMapper, itemJpaRepository)

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

        val productDomain = br.com.manafood.manafoodproduct.domain.model.Product(
            id = productJpa.id,
            name = productJpa.name,
            description = productJpa.description ?: "",
            unitPrice = productJpa.unitPrice,
            categoryId = categoryJpa.id,
            category = br.com.manafood.manafoodproduct.domain.model.Category(
                id = categoryJpa.id,
                name = categoryJpa.name,
                createdBy = categoryJpa.createdBy,
                deleted = false
            ),
            items = mutableListOf(),
            createdBy = productJpa.createdBy,
            deleted = false
        )

        every { springRepo.findByIdAndNotDeleted(productJpa.id) } returns Optional.of(productJpa)
        every { productEntityMapper.toDomain(productJpa) } returns productDomain

        // When
        val result = adapter.findById(productJpa.id)

        // Then
        assertNotNull(result)
        assertEquals(productJpa.id, result.id)
        assertEquals(productJpa.name, result.name)
        verify(exactly = 1) { springRepo.findByIdAndNotDeleted(productJpa.id) }
        verify(exactly = 1) { productEntityMapper.toDomain(productJpa) }
    }

    @Test
    fun `findById should return null when not found`() {
        // Given
        val id = UUID.randomUUID()
        every { springRepo.findByIdAndNotDeleted(id) } returns Optional.empty()

        // When
        val result = adapter.findById(id)

        // Then
        assertNull(result)
        verify(exactly = 1) { springRepo.findByIdAndNotDeleted(id) }
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

        val productDomain = br.com.manafood.manafoodproduct.domain.model.Product(
            id = productJpa.id,
            name = productJpa.name,
            description = productJpa.description ?: "",
            unitPrice = productJpa.unitPrice,
            categoryId = categoryJpa.id,
            category = br.com.manafood.manafoodproduct.domain.model.Category(
                id = categoryJpa.id,
                name = categoryJpa.name,
                createdBy = categoryJpa.createdBy,
                deleted = false
            ),
            items = mutableListOf(),
            createdBy = productJpa.createdBy,
            deleted = false
        )

        val page = PageImpl(listOf(productJpa), PageRequest.of(0, 10), 1)
        val pagedDomain = br.com.manafood.manafoodproduct.domain.common.Paged(
            items = listOf(productDomain),
            page = 0,
            pageSize = 10,
            totalItems = 1L,
            totalPages = 1
        )

        every { springRepo.findPaged(PageRequest.of(0, 10)) } returns page
        every { productEntityMapper.toPagedDomain(page) } returns pagedDomain

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
            categoryId = category.id,
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

        val productJpa = ProductJpaEntity(
            id = product.id,
            name = product.name,
            description = product.description,
            unitPrice = product.unitPrice,
            category = categoryJpa,
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = product.createdBy
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

        every { productEntityMapper.toEntity(product) } returns productJpa
        every { springRepo.findById(product.id) } returns Optional.empty()
        every { springRepo.save(productJpa) } returns savedJpa
        every { productEntityMapper.toDomain(savedJpa) } returns product

        // When
        val result = adapter.save(product)

        // Then
        assertNotNull(result)
        assertEquals(product.id, result.id)
        assertEquals(product.name, result.name)
        verify(exactly = 1) { springRepo.save(productJpa) }
        verify(exactly = 1) { productEntityMapper.toEntity(product) }
        verify(exactly = 1) { productEntityMapper.toDomain(savedJpa) }
    }

    @Test
    fun `findPaged should return empty list when no products`() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl<ProductJpaEntity>(emptyList(), pageable, 0)
        val emptyPaged = br.com.manafood.manafoodproduct.domain.common.Paged<br.com.manafood.manafoodproduct.domain.model.Product>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )

        every { springRepo.findPaged(pageable) } returns page
        every { productEntityMapper.toPagedDomain(page) } returns emptyPaged

        val result = adapter.findPaged(0, 10)

        assertEquals(0, result.items.size)
        assertEquals(0, result.totalItems)
    }

    @Test
    fun `deleteById should delete product and return true`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns false

        val result = adapter.deleteById(id)

        assertEquals(true, result)
        verify(exactly = 1) { springRepo.deleteById(id) }
    }

    @Test
    fun `deleteById should return false when product still exists`() {
        val id = UUID.randomUUID()
        every { springRepo.deleteById(id) } returns Unit
        every { springRepo.existsByIdAndNotDeleted(id) } returns true

        val result = adapter.deleteById(id)

        assertEquals(false, result)
    }

    @Test
    fun `findById with product without description`() {
        val categoryJpa = CategoryJpaEntity(
            id = UUID.randomUUID(),
            name = "Category X",
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val productJpa = ProductJpaEntity(
            id = UUID.randomUUID(),
            name = "Product X",
            description = null,
            unitPrice = BigDecimal("100.0"),
            category = categoryJpa,
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID()
        )

        val productDomain = br.com.manafood.manafoodproduct.domain.model.Product(
            id = productJpa.id,
            name = productJpa.name,
            description = "",
            unitPrice = productJpa.unitPrice,
            categoryId = categoryJpa.id,
            category = br.com.manafood.manafoodproduct.domain.model.Category(
                id = categoryJpa.id,
                name = categoryJpa.name,
                createdBy = categoryJpa.createdBy,
                deleted = false
            ),
            items = mutableListOf(),
            createdBy = productJpa.createdBy,
            deleted = false
        )

        every { springRepo.findByIdAndNotDeleted(productJpa.id) } returns Optional.of(productJpa)
        every { productEntityMapper.toDomain(productJpa) } returns productDomain

        val result = adapter.findById(productJpa.id)

        assertNotNull(result)
        // Description pode ser null ou string vazia dependendo do mapeamento
        assertTrue(result.description.isNullOrEmpty())
    }
}

