package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.product.commands.create.CreateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.delete.DeleteProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.update.UpdateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getall.GetAllProductsUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getbyid.GetProductByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductUseCaseConfig(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
) {

    @Bean
    fun createProductUseCase(): CreateProductUseCase {
        return CreateProductUseCase(productRepository, categoryRepository, itemRepository)
    }

    @Bean
    fun updateProductUseCase(): UpdateProductUseCase {
        return UpdateProductUseCase(productRepository, categoryRepository, itemRepository)
    }

    @Bean
    fun deleteProductUseCase(): DeleteProductUseCase {
        return DeleteProductUseCase(productRepository)
    }

    @Bean
    fun getProductByIdUseCase(): GetProductByIdUseCase {
        return GetProductByIdUseCase(productRepository)
    }

    @Bean
    fun getAllProductsUseCase(): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }
}
