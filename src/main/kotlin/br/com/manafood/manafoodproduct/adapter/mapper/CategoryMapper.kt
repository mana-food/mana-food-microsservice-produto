package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodproduct.application.usecase.category.commands.create.CreateCategoryCommand
import br.com.manafood.manafoodproduct.application.usecase.category.commands.delete.DeleteCategoryCommand
import br.com.manafood.manafoodproduct.application.usecase.category.commands.update.UpdateCategoryCommand
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Category
import java.util.*

object CategoryMapper {
    fun toCreateCommand(request: CreateCategoryRequest, createdBy: UUID) =
        CreateCategoryCommand(
            name = request.name,
            createdBy = createdBy
        )

    fun toUpdateCommand(request: UpdateCategoryRequest, updatedBy: UUID) =
        UpdateCategoryCommand(
            id = request.id,
            name = request.name,
            updatedBy = updatedBy
        )

    fun toDeleteCommand(id: UUID, deletedBy: UUID) =
        DeleteCategoryCommand(
            id = id,
            deletedBy = deletedBy
        )

    fun toResponse(category: Category): CategoryResponse =
        CategoryResponse(
            id = category.id,
            name = category.name,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )

    fun toResponsePaged(categoriesPaged: Paged<Category>): Paged<CategoryResponse> {
        val categoryResponses = categoriesPaged.items.map { toResponse(it) }
        return Paged(
            items = categoryResponses,
            page = categoriesPaged.page,
            pageSize = categoriesPaged.pageSize,
            totalItems = categoriesPaged.totalItems,
            totalPages = categoriesPaged.totalPages
        )
    }
}
