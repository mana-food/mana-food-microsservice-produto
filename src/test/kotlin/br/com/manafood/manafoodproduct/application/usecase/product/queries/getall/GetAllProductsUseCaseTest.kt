package br.com.manafood.manafoodproduct.application.usecase.product.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllProductsUseCaseTest {

    private val productRepository = mockk<ProductRepository>()
    private val useCase = GetAllProductsUseCase(productRepository)

    @Test
    fun `execute should return paged products when products exist`() {
        // Given
        val product1 = Fixtures.sampleProduct()
        val product2 = Fixtures.sampleProduct().copy(name = "Product 2")
        val pagedProducts = Paged(
            items = listOf(product1, product2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )
        val query = GetAllProductsQuery(page = 0, pageSize = 10)

        every { productRepository.findPaged(0, 10) } returns pagedProducts

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(2, result.items.size)
        assertEquals(2L, result.totalItems)
        assertEquals(0, result.page)
        assertEquals(10, result.pageSize)
        assertEquals(1, result.totalPages)
        verify(exactly = 1) { productRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should return empty paged when no products exist`() {
        // Given
        val emptyPaged = Paged<br.com.manafood.manafoodproduct.domain.model.Product>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )
        val query = GetAllProductsQuery(page = 0, pageSize = 10)

        every { productRepository.findPaged(0, 10) } returns emptyPaged

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(0, result.items.size)
        assertEquals(0L, result.totalItems)
        verify(exactly = 1) { productRepository.findPaged(0, 10) }
    }

    @Test
    fun `execute should handle different page sizes correctly`() {
        // Given
        val product = Fixtures.sampleProduct()
        val pagedProducts = Paged(
            items = listOf(product),
            page = 1,
            pageSize = 20,
            totalItems = 50L,
            totalPages = 3
        )
        val query = GetAllProductsQuery(page = 1, pageSize = 20)

        every { productRepository.findPaged(1, 20) } returns pagedProducts

        // When
        val result = useCase.execute(query)

        // Then
        assertEquals(1, result.items.size)
        assertEquals(1, result.page)
        assertEquals(20, result.pageSize)
        assertEquals(50L, result.totalItems)
        assertEquals(3, result.totalPages)
        verify(exactly = 1) { productRepository.findPaged(1, 20) }
    }
}

