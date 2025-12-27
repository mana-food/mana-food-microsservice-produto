package br.com.manafood.manafoodpoduct.adapter.mapper

import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodpoduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodpoduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodpoduct.adapter.response.common.CategorySummaryResponse
import br.com.manafood.manafoodpoduct.adapter.response.item.ItemResponse
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.create.CreateItemCommand
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.delete.DeleteItemCommand
import br.com.manafood.manafoodpoduct.application.usecase.item.commands.update.UpdateItemCommand
import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Category
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
            categoryId = request.categoryId,
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

    fun toResponsePaged(itemPaged: Paged<Item>): Paged<ItemResponse> {
        val itemResponses = itemPaged.items.map { toResponse(it) }
        return Paged(
            items = itemResponses,
            page = itemPaged.page,
            pageSize = itemPaged.pageSize,
            totalItems = itemPaged.totalItems,
            totalPages = itemPaged.totalPages
        )
    }
}
