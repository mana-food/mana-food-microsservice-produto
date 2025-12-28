package br.com.manafood.manafoodproduct.application.usecase.product.commands.create

import java.util.*

data class CreateProductCommand(
    val name: String,
    val description: String?,
    val unitPrice: Double,
    val categoryId: UUID,
    val itemIds: List<UUID> = emptyList(),
    val createdBy: UUID
)