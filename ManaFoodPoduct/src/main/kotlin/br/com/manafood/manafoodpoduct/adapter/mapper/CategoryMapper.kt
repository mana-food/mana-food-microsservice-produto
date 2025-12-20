package br.com.manafood.manafoodpoduct.adapter.mapper

import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodpoduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodpoduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodpoduct.application.usecase.category.commands.create.CreateCategoryCommand
import br.com.manafood.manafoodpoduct.application.usecase.category.commands.delete.DeleteCategoryCommand
import br.com.manafood.manafoodpoduct.application.usecase.category.commands.update.UpdateCategoryCommand
import br.com.manafood.manafoodpoduct.domain.model.Category
import java.util.*

object CategoryMapper {
    fun toCreateCommand(request: CreateCategoryRequest, createdBy: UUID) =
        CreateCategoryCommand(
            name = request.name,
            description = request.description,
            createdBy = createdBy
        )

    fun toUpdateCommand(request: UpdateCategoryRequest, updatedBy: UUID) =
        UpdateCategoryCommand(
            id = request.id,
            name = request.name,
            description = request.description,
            updatedBy = updatedBy
        )

    fun toDeleteCommand(id: UUID, deletedBy: UUID) =
        DeleteCategoryCommand(
            id = id,
            deletedBy = deletedBy
        )

    fun toResponse(category: Category): CategoryResponse =
        CategoryResponse(
            id = category.id!!,
            name = category.name,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )

}
