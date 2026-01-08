package br.com.manafood.manafoodproduct.application.usecase.product.queries.getbyid

import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.testutil.Fixtures
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import java.util.*

class GetProductByIdUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private val useCase = GetProductByIdUseCase(repository)

    @Test
    fun `execute should return product when found`() {
        val product = Fixtures.sampleProduct()
        every { repository.findById(product.id) } returns product

        val result = useCase.execute(GetProductByIdQuery(product.id))

        assertEquals(product, result)
    }

    @Test
    fun `execute should return null when not found`() {
        val id = UUID.randomUUID()
        every { repository.findById(id) } returns null

        val result = useCase.execute(GetProductByIdQuery(id))

        assertNull(result)
    }
}

