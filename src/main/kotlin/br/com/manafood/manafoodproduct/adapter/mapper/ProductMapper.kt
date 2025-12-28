package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodproduct.adapter.response.common.CategorySummaryResponse
import br.com.manafood.manafoodproduct.adapter.response.product.ProductResponse
import br.com.manafood.manafoodproduct.application.usecase.product.commands.create.CreateProductCommand
import br.com.manafood.manafoodproduct.application.usecase.product.commands.delete.DeleteProductCommand
import br.com.manafood.manafoodproduct.application.usecase.product.commands.update.UpdateProductCommand
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Product
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
            categoryId = request.categoryId,
            updatedBy = updatedBy
        )

    fun toDeleteCommand(id: UUID, deletedBy: UUID) =
        DeleteProductCommand(id, deletedBy)

    fun toResponse(product: Product): ProductResponse =
        ProductResponse(
            id = product.id,
            name = product.name,
            description = product.description,
            unitPrice = product.unitPrice,
            category = CategorySummaryResponse(
                id = product.category.id,
                name = product.category.name
            ),
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )

    fun toResponsePaged(productPaged: Paged<Product>): Paged<ProductResponse> {
        val productResponses = productPaged.items.map { toResponse(it) }
        return Paged(
            items = productResponses,
            page = productPaged.page,
            pageSize = productPaged.pageSize,
            totalItems = productPaged.totalItems,
            totalPages = productPaged.totalPages
        )
    }
}
