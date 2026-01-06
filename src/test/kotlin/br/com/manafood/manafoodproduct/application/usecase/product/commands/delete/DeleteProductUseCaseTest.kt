package br.com.manafood.manafoodproduct.application.usecase.product.commands.delete

import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
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

class DeleteProductUseCaseTest {

    private val productRepository = mockk<ProductRepository>()
    private val useCase = DeleteProductUseCase(productRepository)

    @Test
    fun `execute should delete product successfully when product exists`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val command = DeleteProductCommand(
            id = existingProduct.id!!,
            deletedBy = UUID.randomUUID()
        )

        val productSlot = slot<br.com.manafood.manafoodproduct.domain.model.Product>()

        every { productRepository.findById(command.id) } returns existingProduct
        every { productRepository.save(capture(productSlot)) } returns existingProduct.copy(deleted = true)

        // When
        useCase.execute(command)

        // Then
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { productRepository.save(any()) }

        val capturedProduct = productSlot.captured
        assertTrue(capturedProduct.deleted)
        assertEquals(command.deletedBy, capturedProduct.updatedBy)
    }

    @Test
    fun `execute should throw IllegalArgumentException when product not found`() {
        // Given
        val command = DeleteProductCommand(
            id = UUID.randomUUID(),
            deletedBy = UUID.randomUUID()
        )

        every { productRepository.findById(command.id) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_PRODUCT_USE_CASE] Produto não encontrado para o id [${command.id}].", exception.message)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `execute should throw Exception when DataAccessException occurs`() {
        // Given
        val existingProduct = Fixtures.sampleProduct()
        val command = DeleteProductCommand(
            id = existingProduct.id!!,
            deletedBy = UUID.randomUUID()
        )

        every { productRepository.findById(command.id) } returns existingProduct
        every { productRepository.save(any()) } throws mockk<DataAccessException>()

        // When & Then
        val exception = assertThrows<Exception> {
            useCase.execute(command)
        }

        assertEquals("[DELETE_PRODUCT_USE_CASE] Falha ao tentar deletar o produto", exception.message)
        verify(exactly = 1) { productRepository.findById(command.id) }
        verify(exactly = 1) { productRepository.save(any()) }
    }
}

