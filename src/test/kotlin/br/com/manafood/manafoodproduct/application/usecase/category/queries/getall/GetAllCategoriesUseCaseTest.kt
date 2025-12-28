package br.com.manafood.manafoodproduct.application.usecase.category.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllCategoriesUseCaseTest {

    private val categoryRepository = mockk<CategoryRepository>()
    private val useCase = GetAllCategoriesUseCase(categoryRepository)

    @Test
    fun `execute should return paged categories when categories exist`() {
        // Given
        val category1 = Fixtures.sampleCategory()
        val category2 = Fixtures.sampleCategory().copy(name = "Category 2")
        val pagedCategories = Paged(
            items = listOf(category1, category2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )
        val query = GetAllCategoriesQuery(page = 0, pageSize = 10)

        every { categoryRepository.findPaged(0, 10) } returns pagedCategories

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(2, result.items.size)
        assertEquals(2L, result.totalItems)
        assertEquals(0, result.page)
        assertEquals(10, result.pageSize)
        assertEquals(1, result.totalPages)
        verify(exactly = 1) { categoryRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should return empty paged when no categories exist`() {
        // Given
        val emptyPaged = Paged<br.com.manafood.manafoodproduct.domain.model.Category>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )
        val query = GetAllCategoriesQuery(page = 0, pageSize = 10)

        every { categoryRepository.findPaged(0, 10) } returns emptyPaged

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(0, result.items.size)
        assertEquals(0L, result.totalItems)
        verify(exactly = 1) { categoryRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should handle different page sizes correctly`() {
        // Given
        val category = Fixtures.sampleCategory()
        val pagedCategories = Paged(
            items = listOf(category),
            page = 3,
            pageSize = 15,
            totalItems = 100L,
            totalPages = 7
        )
        val query = GetAllCategoriesQuery(page = 3, pageSize = 15)

        every { categoryRepository.findPaged(3, 15) } returns pagedCategories

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(1, result.items.size)
        assertEquals(3, result.page)
        assertEquals(15, result.pageSize)
        assertEquals(100L, result.totalItems)
        assertEquals(7, result.totalPages)
        verify(exactly = 1) { categoryRepository.findPaged(3, 15) }
    }
}

