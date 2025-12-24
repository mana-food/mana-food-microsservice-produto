package br.com.manafood.manafoodpoduct.infrastructure.config

import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.CategoryJpaRepositoryImpl
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.ItemJpaRepositoryImpl
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.ProductJpaRepositoryImpl
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring.SpringCategoryJpaRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring.SpringItemJpaRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring.SpringProductJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean
    fun productRepository(
        springRepo: SpringProductJpaRepository
    ): ProductRepository = ProductJpaRepositoryImpl(springRepo)

    @Bean
    fun itemRepository(
        springRepo: SpringItemJpaRepository
    ): ItemRepository = ItemJpaRepositoryImpl(springRepo)

    @Bean
    fun categoryRepository(
        springRepo: SpringCategoryJpaRepository
    ): CategoryRepository = CategoryJpaRepositoryImpl(springRepo)
}
