package br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid

import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetItemByIdUseCaseTest {

    private val repository = mockk<ItemRepository>()
    private val useCase = GetItemByIdUseCase(repository)

    @Test
    fun `execute returns item when found`() {
        val item = Fixtures.sampleItem()
        every { repository.findById(item.id!!) } returns item

        val result = useCase.execute(GetItemByIdQuery(item.id!!))

        assertEquals(item, result)
    }

    @Test
    fun `execute returns null when not found`() {
        val id = java.util.UUID.randomUUID()
        every { repository.findById(id) } returns null

        val result = useCase.execute(GetItemByIdQuery(id))
        assertNull(result)
    }
}

