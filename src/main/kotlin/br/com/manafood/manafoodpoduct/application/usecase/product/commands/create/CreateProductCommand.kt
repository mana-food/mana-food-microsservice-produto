package br.com.manafood.manafoodpoduct.application.usecase.product.commands.create

import java.util.*

data class CreateProductCommand(
    val name: String,
    val description: String?,
    val unitPrice: Double,
    val categoryId: UUID,
    val itemIds: List<UUID> = emptyList(),
    val createdBy: UUID
)