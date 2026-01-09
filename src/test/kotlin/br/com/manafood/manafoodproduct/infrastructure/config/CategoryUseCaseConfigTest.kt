package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.category.commands.create.CreateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.delete.DeleteCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.update.UpdateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class CategoryUseCaseConfigTest {

    private val categoryRepository = mockk<CategoryRepository>()
    private val config = CategoryUseCaseConfig(categoryRepository)

    @Test
    fun `createCategoryUseCase should return CreateCategoryUseCase instance`() {
        // When
        val result = config.createCategoryUseCase()

        // Then
        assertNotNull(result)
        assert(result is CreateCategoryUseCase)
    }

    @Test
    fun `updateCategoryUseCase should return UpdateCategoryUseCase instance`() {
        // When
        val result = config.updateCategoryUseCase()

        // Then
        assertNotNull(result)
        assert(result is UpdateCategoryUseCase)
    }

    @Test
    fun `deleteCategoryUseCase should return DeleteCategoryUseCase instance`() {
        // When
        val result = config.deleteCategoryUseCase()

        // Then
        assertNotNull(result)
        assert(result is DeleteCategoryUseCase)
    }

    @Test
    fun `getCategoryByIdUseCase should return GetCategoryByIdUseCase instance`() {
        // When
        val result = config.getCategoryByIdUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetCategoryByIdUseCase)
    }

    @Test
    fun `getAllCategorysUseCase should return GetAllCategoriesUseCase instance`() {
        // When
        val result = config.getAllCategorysUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetAllCategoriesUseCase)
    }
}

