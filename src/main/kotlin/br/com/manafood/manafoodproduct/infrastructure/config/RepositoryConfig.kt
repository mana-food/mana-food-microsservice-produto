package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.CategoryJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.ItemJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.adapter.ProductJpaRepositoryAdapter
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.CategoryJpaRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ProductJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean
    fun productRepository(
        springRepo: ProductJpaRepository
    ): ProductRepository = ProductJpaRepositoryAdapter(springRepo)

    @Bean
    fun itemRepository(
        springRepo: ItemJpaRepository
    ): ItemRepository = ItemJpaRepositoryAdapter(springRepo)

    @Bean
    fun categoryRepository(
        springRepo: CategoryJpaRepository
    ): CategoryRepository = CategoryJpaRepositoryAdapter(springRepo)
}
