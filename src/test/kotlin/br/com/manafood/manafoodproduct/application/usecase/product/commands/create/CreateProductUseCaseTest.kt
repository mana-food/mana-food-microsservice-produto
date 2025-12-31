package br.com.manafood.manafoodproduct.application.usecase.product.commands.create

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataAccessException
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CreateProductUseCaseTest {

    private val productRepository = mockk<ProductRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val itemRepository = mockk<ItemRepository>()
    private val useCase = CreateProductUseCase(productRepository, categoryRepository, itemRepository)

    @Test
    fun `execute should create product successfully when category and items exist`() {
        // Given
        val category = Fixtures.sampleCategory()
        val item = Fixtures.sampleItem()
        val command = CreateProductCommand(
            name = "New Product",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            categoryId = category.id,
            itemIds = listOf(item.id),
            createdBy = UUID.randomUUID()
        )

        val productSlot = slot<br.com.manafood.manafoodproduct.domain.model.Product>()
        val savedProduct = Fixtures.sampleProduct()

        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(command.itemIds) } returns listOf(item)
        every { productRepository.save(capture(productSlot)) } returns savedProduct

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(savedProduct.id, result.id)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(command.itemIds) }
        verify(exactly = 1) { productRepository.save(any()) }

        val capturedProduct = productSlot.captured
        assertEquals(command.name, capturedProduct.name)
        assertEquals(command.unitPrice, capturedProduct.unitPrice)
        assertEquals(category, capturedProduct.category)
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val command = CreateProductCommand(
            name = "New Product",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            categoryId = UUID.randomUUID(),
            itemIds = listOf(UUID.randomUUID()),
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.categoryId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_PRODUCT_USE_CASE] Categoria com id ${command.categoryId} não encontrada.", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 0) { itemRepository.findByIds(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `execute should throw IllegalArgumentException when items not found`() {
        // Given
        val category = Fixtures.sampleCategory()
        val command = CreateProductCommand(
            name = "New Product",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            categoryId = category.id,
            itemIds = listOf(UUID.randomUUID()),
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(command.itemIds) } returns emptyList()

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_PRODUCT_USE_CASE] Item com id ${command.categoryId} não encontrada.", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(command.itemIds) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val category = Fixtures.sampleCategory()
        val item = Fixtures.sampleItem()
        val command = CreateProductCommand(
            name = "New Product",
            description = "Description",
            unitPrice = BigDecimal("100.0"),
            categoryId = category.id,
            itemIds = listOf(item.id),
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(command.itemIds) } returns listOf(item)
        every { productRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_PRODUCT_USE_CASE] Falha ao tentar criar o produto", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(command.itemIds) }
        verify(exactly = 1) { productRepository.save(any()) }
    }
}

