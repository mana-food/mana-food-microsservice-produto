package br.com.manafood.manafoodproduct.application.usecase.category.commands.update

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
import kotlin.test.assertNotNull

class UpdateCategoryUseCaseTest {

    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = UpdateCategoryUseCase(categoryRepository)

    @Test
    fun `execute should update category successfully when category exists`() {
        // Given
        val existingCategory = Fixtures.sampleCategory()
        val command = UpdateCategoryCommand(
            id = existingCategory.id!!,
            name = "Updated Category",
            updatedBy = UUID.randomUUID()
        )

        val categorySlot = slot<br.com.manafood.manafoodproduct.domain.model.Category>()
        val updatedCategory = existingCategory.copy(
            name = command.name,
            updatedBy = command.updatedBy
        )

        every { categoryRepository.findById(command.id) } returns existingCategory
        every { categoryRepository.save(capture(categorySlot)) } returns updatedCategory

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(updatedCategory.name, result.name)
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.save(any()) }

        val capturedCategory = categorySlot.captured
        assertEquals(command.name, capturedCategory.name)
        assertEquals(command.updatedBy, capturedCategory.updatedBy)
    }

    @Test
    fun `execute should throw IllegalArgumentException when category not found`() {
        // Given
        val command = UpdateCategoryCommand(
            id = UUID.randomUUID(),
            name = "Updated Category",
            updatedBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_CATEGORY_USE_CASE] Categoria não encontrada", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 0) { categoryRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingCategory = Fixtures.sampleCategory()
        val command = UpdateCategoryCommand(
            id = existingCategory.id!!,
            name = "Updated Category",
            updatedBy = UUID.randomUUID()
        )

        every { categoryRepository.findById(command.id) } returns existingCategory
        every { categoryRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[UPDATE_CATEGORY_USE_CASE] Erro ao atualizar entidade", exception.message)
        verify(exactly = 1) { categoryRepository.findById(command.id) }
        verify(exactly = 1) { categoryRepository.save(any()) }
    }
}

