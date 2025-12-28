package br.com.manafood.manafoodproduct.application.usecase.item.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllItemsUseCaseTest {

    private val itemRepository = mockk<ItemRepository>()
    private val useCase = GetAllItemsUseCase(itemRepository)

    @Test
    fun `execute should return paged items when items exist`() {
        // Given
        val item1 = Fixtures.sampleItem()
        val item2 = Fixtures.sampleItem().copy(name = "Item 2")
        val pagedItems = Paged(
            items = listOf(item1, item2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )
        val query = GetAllItemsQuery(page = 0, pageSize = 10)

        every { itemRepository.findPaged(0, 10) } returns pagedItems

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(2, result.items.size)
        assertEquals(2L, result.totalItems)
        assertEquals(0, result.page)
        assertEquals(10, result.pageSize)
        assertEquals(1, result.totalPages)
        verify(exactly = 1) { itemRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should return empty paged when no items exist`() {
        // Given
        val emptyPaged = Paged<br.com.manafood.manafoodproduct.domain.model.Item>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )
        val query = GetAllItemsQuery(page = 0, pageSize = 10)

        every { itemRepository.findPaged(0, 10) } returns emptyPaged

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(0, result.items.size)
        assertEquals(0L, result.totalItems)
        verify(exactly = 1) { itemRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should handle different page sizes correctly`() {
        // Given
        val item = Fixtures.sampleItem()
        val pagedItems = Paged(
            items = listOf(item),
            page = 2,
            pageSize = 5,
            totalItems = 20L,
            totalPages = 4
        )
        val query = GetAllItemsQuery(page = 2, pageSize = 5)

        every { itemRepository.findPaged(2, 5) } returns pagedItems

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(1, result.items.size)
        assertEquals(2, result.page)
        assertEquals(5, result.pageSize)
        assertEquals(20L, result.totalItems)
        assertEquals(4, result.totalPages)
        verify(exactly = 1) { itemRepository.findPaged(2, 5) }
    }
}

