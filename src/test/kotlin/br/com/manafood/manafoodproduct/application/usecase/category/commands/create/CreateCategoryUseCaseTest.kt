package br.com.manafood.manafoodproduct.application.usecase.category.commands.create

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

class CreateCategoryUseCaseTest {

    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = CreateCategoryUseCase(categoryRepository)

    @Test
    fun `execute should create category successfully`() {
        // Given
        val command = CreateCategoryCommand(
            name = "New Category",
            createdBy = UUID.randomUUID()
        )

        val categorySlot = slot<br.com.manafood.manafoodproduct.domain.model.Category>()
        val savedCategory = Fixtures.sampleCategory()

        every { categoryRepository.save(capture(categorySlot)) } returns savedCategory

        // When
        val result = useCase.execute(command)

        // Then
        assertNotNull(result)
        assertEquals(savedCategory.id, result.id)
        verify(exactly = 1) { categoryRepository.save(any()) }

        val capturedCategory = categorySlot.captured
        assertEquals(command.name, capturedCategory.name)
        assertEquals(command.createdBy, capturedCategory.createdBy)
        assertEquals(false, capturedCategory.deleted)
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val command = CreateCategoryCommand(
            name = "New Category",
            createdBy = UUID.randomUUID()
        )

        every { categoryRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[CREATE_CATEGORY_USE_CASE] Falha ao tentar criar a categoria", exception.message)
        verify(exactly = 1) { categoryRepository.save(any()) }
    }
}

