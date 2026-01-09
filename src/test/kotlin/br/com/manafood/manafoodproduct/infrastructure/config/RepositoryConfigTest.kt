package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.CategoryJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.ItemJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.ProductJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.mapper.ProductEntityMapper
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.CategoryJpaRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ProductJpaRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class RepositoryConfigTest {

    private val config = RepositoryConfig()

    @Test
    fun `productRepository should return ProductRepository instance`() {
        // Given
        val springRepo = mockk<ProductJpaRepository>()
        val productEntityMapper = mockk<ProductEntityMapper>()
        val itemJpaRepository = mockk<ItemJpaRepository>()

        // When
        val result = config.productRepository(springRepo, productEntityMapper, itemJpaRepository)

        // Then
        assertNotNull(result)
        assert(result is ProductRepository)
        assert(result is ProductJpaRepositoryAdapter)
    }

    @Test
    fun `itemRepository should return ItemRepository instance`() {
        // Given
        val springRepo = mockk<ItemJpaRepository>()

        // When
        val result = config.itemRepository(springRepo)

        // Then
        assertNotNull(result)
        assert(result is ItemRepository)
        assert(result is ItemJpaRepositoryAdapter)
    }

    @Test
    fun `categoryRepository should return CategoryRepository instance`() {
        // Given
        val springRepo = mockk<CategoryJpaRepository>()

        // When
        val result = config.categoryRepository(springRepo)

        // Then
        assertNotNull(result)
        assert(result is CategoryRepository)
        assert(result is CategoryJpaRepositoryAdapter)
    }
}

