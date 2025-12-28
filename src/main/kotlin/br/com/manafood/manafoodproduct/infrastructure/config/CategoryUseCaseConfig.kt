package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.category.commands.create.CreateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.delete.DeleteCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.update.UpdateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CategoryUseCaseConfig(
    private val categoryRepository: CategoryRepository
) {

    @Bean
    fun createCategoryUseCase(): CreateCategoryUseCase {
        return CreateCategoryUseCase(categoryRepository)
    }

    @Bean
    fun updateCategoryUseCase(): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(categoryRepository)
    }

    @Bean
    fun deleteCategoryUseCase(): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(categoryRepository)
    }

    @Bean
    fun getCategoryByIdUseCase(): GetCategoryByIdUseCase {
        return GetCategoryByIdUseCase(categoryRepository)
    }

    @Bean
    fun getAllCategorysUseCase(): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(categoryRepository)
    }
}
