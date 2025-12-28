package br.com.manafood.manafoodproduct.application.usecase.product.commands.update

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

class UpdateProductUseCaseTest {

    private val productRepository = mockk<ProductRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val itemRepository = mockk<ItemRepository>()
    private val useCase = UpdateProductUseCase(productRepository, categoryRepository, itemRepository)

    @Test
    fun `execute should update product successfully when product, category and items exist`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val category = Fixtures.sampleCategory()
        val item = Fixtures.sampleItem()
        val command = UpdateProductCommand(
            id = existingProduct.id,
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("150.0"),
            categoryId = category.id,
            itemIds = listOf(item.id),
            updatedBy = UUID.randomUUID()
        )

        val productSlot = slot<br.com.manafood.manafoodproduct.domain.model.Product>()
        val updatedProduct = existingProduct.copy(
            name = command.name,
            description = command.description!!,
            unitPrice = command.unitPrice,
            updatedBy = command.updatedBy
        )

        every { productRepository.findById(command.id) } returns existingProduct
        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(command.itemIds) } returns listOf(item)
        every { productRepository.save(capture(productSlot)) } returns updatedProduct

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(updatedProduct.name, result.name)
        assertEquals(updatedProduct.unitPrice, result.unitPrice)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(command.itemIds) }
        verify(exactly = 1) { productRepository.save(any()) }

        val capturedProduct = productSlot.captured
        assertEquals(command.name, capturedProduct.name)
        assertEquals(command.updatedBy, capturedProduct.updatedBy)
    }

    @Test
    fun `execute should keep existing items when no new items provided`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val category = Fixtures.sampleCategory()
        val command = UpdateProductCommand(
            id = existingProduct.id,
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("150.0"),
            categoryId = category.id,
            itemIds = emptyList(),
            updatedBy = UUID.randomUUID()
        )

        val productSlot = slot<br.com.manafood.manafoodproduct.domain.model.Product>()
        val updatedProduct = existingProduct.copy(name = command.name)

        every { productRepository.findById(command.id) } returns existingProduct
        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(emptyList()) } returns emptyList()
        every { productRepository.save(capture(productSlot)) } returns updatedProduct

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(emptyList()) }
        verify(exactly = 1) { productRepository.save(any()) }

        val capturedProduct = productSlot.captured
        assertEquals(existingProduct.items, capturedProduct.items)
    }

    @Test
    fun `execute should throw IllegalArgumentException when product not found`() {
        // Given
        val command = UpdateProductCommand(
            id = UUID.randomUUID(),
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("150.0"),
            categoryId = UUID.randomUUID(),
            itemIds = emptyList(),
            updatedBy = UUID.randomUUID()
        )

        every { productRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_PRODUCT_USE_CASE] Produto com id ${command.id} não encontrado.", exception.message)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 0) { categoryRepository.findById(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val command = UpdateProductCommand(
            id = existingProduct.id,
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("150.00"),
            categoryId = UUID.randomUUID(),
            itemIds = emptyList(),
            updatedBy = UUID.randomUUID()
        )

        every { productRepository.findById(command.id) } returns existingProduct
        every { categoryRepository.findById(command.categoryId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_PRODUCT_USE_CASE] Categoria com id ${command.categoryId} não encontrada.", exception.message)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val category = Fixtures.sampleCategory()
        val item = Fixtures.sampleItem()
        val command = UpdateProductCommand(
            id = existingProduct.id,
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("150.0"),
            categoryId = category.id,
            itemIds = listOf(item.id),
            updatedBy = UUID.randomUUID()
        )

        every { productRepository.findById(command.id) } returns existingProduct
        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.findByIds(command.itemIds) } returns listOf(item)
        every { productRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_PRODUCT_USE_CASE] Erro ao atualizar entidade", exception.message)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.findByIds(command.itemIds) }
        verify(exactly = 1) { productRepository.save(any()) }
    }
}

