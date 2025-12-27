package br.com.manafood.manafoodpoduct.infrastructure.config

import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.adapter.CategoryJpaRepositoryAdapter
import br.com.manafood.manafoodpoduct.infrastructure.persistence.adapter.ItemJpaRepositoryAdapter
import br.com.manafood.manafoodpoduct.infrastructure.persistence.adapter.ProductJpaRepositoryAdapter
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.CategoryJpaRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.ItemJpaRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.ProductJpaRepository
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
