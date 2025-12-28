package br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetCategoryByIdUseCaseTest {
    private val repository = mockk<CategoryRepository>()
    private val useCase = GetCategoryByIdUseCase(repository)

    @Test
    fun `execute returns category when found`() {
        val category = Fixtures.sampleCategory()
        every { repository.findById(category.id!!) } returns category

        val result = useCase.execute(GetCategoryByIdQuery(category.id!!))
        assertEquals(category, result)
    }

    @Test
    fun `execute returns null when not found`() {
        val id = java.util.UUID.randomUUID()
        every { repository.findById(id) } returns null

        val result = useCase.execute(GetCategoryByIdQuery(id))
        assertNull(result)
    }
}

