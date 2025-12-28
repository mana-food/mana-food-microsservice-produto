package br.com.manafood.manafoodproduct.application.usecase.item.commands.create

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataAccessException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CreateItemUseCaseTest {

    private val itemRepository = mockk<ItemRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = CreateItemUseCase(itemRepository, categoryRepository)

    @Test
    fun `execute should create item successfully when category exists`() {
        // Given
        val category = Fixtures.sampleCategory()
        val command = CreateItemCommand(
            name = "New Item",
            description = "Description",
            categoryId = category.id!!,
            createdBy = UUID.randomUUID()
        )

        val itemSlot = slot<br.com.manafood.manafoodproduct.domain.model.Item>()
        val savedItem = Fixtures.sampleItem()

        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.save(capture(itemSlot)) } returns savedItem

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(savedItem.id, result.id)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.save(any()) }

        val capturedItem = itemSlot.captured
        assertEquals(command.name, capturedItem.name)
        assertEquals(command.description, capturedItem.description)
        assertEquals(category, capturedItem.category)
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val command = CreateItemCommand(
            name = "New Item",
            description = "Description",
            categoryId = UUID.randomUUID(),
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.categoryId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_ITEM_USE_CASE] Categoria com id ${command.categoryId} não encontrada.", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 0) { itemRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val category = Fixtures.sampleCategory()
        val command = CreateItemCommand(
            name = "New Item",
            description = "Description",
            categoryId = category.id!!,
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_ITEM_USE_CASE] Falha ao tentar criar o item", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.save(any()) }
    }
}

