package br.com.manafood.manafoodpoduct.adapter.mapper

import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodpoduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodpoduct.adapter.response.common.CategorySummaryResponse
import br.com.manafood.manafoodpoduct.adapter.response.product.ProductResponse
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.create.CreateProductCommand
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.delete.DeleteProductCommand
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.update.UpdateProductCommand
import br.com.manafood.manafoodpoduct.domain.model.Product
import java.util.*

object ProductMapper {
    fun toCreateCommand(request: CreateProductRequest, createdBy: UUID) =
        CreateProductCommand(
            name = request.name,
            description = request.description,
            unitPrice = request.unitPrice,
            categoryId = request.categoryId,
            itemIds = request.itemIds,
            createdBy = createdBy
        )

    fun toUpdateCommand(request: UpdateProductRequest, updatedBy: UUID) =
        UpdateProductCommand(
            id = request.id,
            name = request.name,
            description = request.description,
            unitPrice = request.unitPrice,
            itemIds = request.itemIds,
            updatedBy = updatedBy
        )

    fun toDeleteCommand(id: UUID, deletedBy: UUID) =
        DeleteProductCommand(id, deletedBy)

    fun toResponse(product: Product): ProductResponse =
        ProductResponse(
            id = product.id!!,
            name = product.name,
            description = product.description,
            unitPrice = product.unitPrice,
            category = CategorySummaryResponse(
                id = product.category.id!!,
                name = product.category.name
            ),
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
}
