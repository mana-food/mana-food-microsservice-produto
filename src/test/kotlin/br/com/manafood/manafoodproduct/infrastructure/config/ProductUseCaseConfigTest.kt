package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.product.commands.create.CreateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.delete.DeleteProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.update.UpdateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getall.GetAllProductsUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getbyid.GetProductByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class ProductUseCaseConfigTest {

    private val productRepository = mockk<ProductRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val itemRepository = mockk<ItemRepository>()
    private val config = ProductUseCaseConfig(productRepository, categoryRepository, itemRepository)

    @Test
    fun `createProductUseCase should return CreateProductUseCase instance`() {
        // When
        val result = config.createProductUseCase()

        // Then
        assertNotNull(result)
        assert(result is CreateProductUseCase)
    }

    @Test
    fun `updateProductUseCase should return UpdateProductUseCase instance`() {
        // When
        val result = config.updateProductUseCase()

        // Then
        assertNotNull(result)
        assert(result is UpdateProductUseCase)
    }

    @Test
    fun `deleteProductUseCase should return DeleteProductUseCase instance`() {
        // When
        val result = config.deleteProductUseCase()

        // Then
        assertNotNull(result)
        assert(result is DeleteProductUseCase)
    }

    @Test
    fun `getProductByIdUseCase should return GetProductByIdUseCase instance`() {
        // When
        val result = config.getProductByIdUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetProductByIdUseCase)
    }

    @Test
    fun `getAllProductsUseCase should return GetAllProductsUseCase instance`() {
        // When
        val result = config.getAllProductsUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetAllProductsUseCase)
    }
}

