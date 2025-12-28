package br.com.manafood.manafoodproduct.application.usecase.item.commands.update

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

class UpdateItemUseCaseTest {

    private val itemRepository = mockk<ItemRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = UpdateItemUseCase(itemRepository, categoryRepository)

    @Test
    fun `execute should update item successfully when item and category exist`() {
        // Given
        val existingItem = Fixtures.sampleItem()
        val category = Fixtures.sampleCategory()
        val command = UpdateItemCommand(
            id = existingItem.id!!,
            name = "Updated Item",
            description = "Updated Description",
            categoryId = category.id!!,
            updatedBy = UUID.randomUUID()
        )

        val itemSlot = slot<br.com.manafood.manafoodproduct.domain.model.Item>()
        val updatedItem = existingItem.copy(
            name = command.name,
            description = command.description,
            category = category,
            updatedBy = command.updatedBy
        )

        every { itemRepository.findById(command.id) } returns existingItem
        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.save(capture(itemSlot)) } returns updatedItem

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(updatedItem.name, result.name)
        assertEquals(updatedItem.description, result.description)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.save(any()) }

        val capturedItem = itemSlot.captured
        assertEquals(command.name, capturedItem.name)
        assertEquals(command.description, capturedItem.description)
        assertEquals(command.updatedBy, capturedItem.updatedBy)
    }

    @Test
    fun `execute should throw IllegalArgumentException when item not found`() {
        // Given
        val command = UpdateItemCommand(
            id = UUID.randomUUID(),
            name = "Updated Item",
            description = "Updated Description",
            categoryId = UUID.randomUUID(),
            updatedBy = UUID.randomUUID()
        )

        every { itemRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_ITEM_USE_CASE] Item com id ${command.categoryId} não encontrado.", exception.message)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 0) { categoryRepository.findById(any()) }
        verify(exactly = 0) { itemRepository.save(any()) }
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val existingItem = Fixtures.sampleItem()
        val command = UpdateItemCommand(
            id = existingItem.id!!,
            name = "Updated Item",
            description = "Updated Description",
            categoryId = UUID.randomUUID(),
            updatedBy = UUID.randomUUID()
        )

        every { itemRepository.findById(command.id) } returns existingItem
        every { categoryRepository.findById(command.categoryId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_ITEM_USE_CASE] Categoria com id ${command.categoryId} não encontrada.", exception.message)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 0) { itemRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingItem = Fixtures.sampleItem()
        val category = Fixtures.sampleCategory()
        val command = UpdateItemCommand(
            id = existingItem.id!!,
            name = "Updated Item",
            description = "Updated Description",
            categoryId = category.id!!,
            updatedBy = UUID.randomUUID()
        )

        every { itemRepository.findById(command.id) } returns existingItem
        every { categoryRepository.findById(command.categoryId) } returns category
        every { itemRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_ITEM_USE_CASE] Erro ao atualizar entidade", exception.message)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.findById(command.categoryId) }
        verify(exactly = 1) { itemRepository.save(any()) }
    }
}

