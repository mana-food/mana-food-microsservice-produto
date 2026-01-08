package br.com.manafood.manafoodproduct.application.usecase.category.commands.delete

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
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

class DeleteCategoryUseCaseTest {

    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = DeleteCategoryUseCase(categoryRepository)

    @Test
    fun `execute should delete category successfully when category exists`() {
        // Given
        val existingCategory = Fixtures.sampleCategory()
        val command = DeleteCategoryCommand(
            id = existingCategory.id,
            deletedBy = UUID.randomUUID()
        )

        val categorySlot = slot<br.com.manafood.manafoodproduct.domain.model.Category>()

        every { categoryRepository.findById(command.id) } returns existingCategory
        every { categoryRepository.save(capture(categorySlot)) } returns existingCategory.copy(deleted = true)

        // When
        useCase.execute(command)

        // Then
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.save(any()) }

        val capturedCategory = categorySlot.captured
        assertTrue(capturedCategory.deleted)
        assertEquals(command.deletedBy, capturedCategory.updatedBy)
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val command = DeleteCategoryCommand(
            id = UUID.randomUUID(),
            deletedBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_CATEGORY_USE_CASE] Categoria com id ${command.id} não encontrada.", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 0) { categoryRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingCategory = Fixtures.sampleCategory()
        val command = DeleteCategoryCommand(
            id = existingCategory.id,
            deletedBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.id) } returns existingCategory
        every { categoryRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_CATEGORY_USE_CASE] Falha ao tentar deletar a categoria", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.save(any()) }
    }
}

