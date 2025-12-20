package br.com.manafood.manafoodpoduct.adapter.mapper

import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodpoduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodpoduct.adapter.response.common.CategorySummaryResponse
import br.com.manafood.manafoodpoduct.adapter.response.item.ItemResponse
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.create.CreateItemCommand
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.delete.DeleteItemCommand
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.update.UpdateItemCommand
import br.com.manafood.manafoodpoduct.domain.model.Item
import java.util.*

object ItemMapper {

    fun toCreateCommand(request: CreateItemRequest, createdBy: UUID) =
        CreateItemCommand(
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            createdBy = createdBy
        )

    fun toUpdateCommand(request: UpdateItemRequest, updatedBy: UUID) =
        UpdateItemCommand(
            id = request.id,
            name = request.name,
            description = request.description,
            updatedBy = updatedBy
        )

    fun toDeleteCommand(id: UUID, deletedBy: UUID) =
        DeleteItemCommand(
            id = id,
            deletedBy = deletedBy
        )

    fun toResponse(item: Item): ItemResponse =
        ItemResponse(
            id = item.id!!,
            name = item.name,
            description = item.description,
            category = CategorySummaryResponse(
                id = item.category.id!!,
                name = item.category.name
            ),
            createdAt = item.createdAt,
            updatedAt = item.updatedAt
        )
}
