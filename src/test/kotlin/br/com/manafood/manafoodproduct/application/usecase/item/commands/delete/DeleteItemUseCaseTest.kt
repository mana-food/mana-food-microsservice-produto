package br.com.manafood.manafoodproduct.application.usecase.item.commands.delete

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
import kotlin.test.assertTrue

class DeleteItemUseCaseTest {

    private val itemRepository = mockk<ItemRepository>()
    private val useCase = DeleteItemUseCase(itemRepository)

    @Test
    fun `execute should delete item successfully when item exists`() {
        // Given
        val existingItem = Fixtures.sampleItem()
        val command = DeleteItemCommand(
            id = existingItem.id!!,
            deletedBy = UUID.randomUUID()
        )

        val itemSlot = slot<br.com.manafood.manafoodproduct.domain.model.Item>()

        every { itemRepository.findById(command.id) } returns existingItem
        every { itemRepository.save(capture(itemSlot)) } returns existingItem.copy(deleted = true)

        // When
        useCase.execute(command)

        // Then
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 1) { itemRepository.save(any()) }

        val capturedItem = itemSlot.captured
        assertTrue(capturedItem.deleted)
        assertEquals(command.deletedBy, capturedItem.updatedBy)
    }

    @Test
    fun `execute should throw IllegalArgumentException when item not found`() {
        // Given
        val command = DeleteItemCommand(
            id = UUID.randomUUID(),
            deletedBy = UUID.randomUUID()
        )

        every { itemRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_ITEM_USE_CASE] Item com id ${command.id} não encontrado.", exception.message)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 0) { itemRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingItem = Fixtures.sampleItem()
        val command = DeleteItemCommand(
            id = existingItem.id!!,
            deletedBy = UUID.randomUUID()
        )

        every { itemRepository.findById(command.id) } returns existingItem
        every { itemRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_ITEM_USE_CASE] Falha ao tentar deletar o item", exception.message)
        verify(exactly = 1) { itemRepository.findById(command.id) }
        verify(exactly = 1) { itemRepository.save(any()) }
    }
}

