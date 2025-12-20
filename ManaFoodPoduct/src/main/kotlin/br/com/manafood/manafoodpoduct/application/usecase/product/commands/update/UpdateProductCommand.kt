package br.com.manafood.manafoodpoduct.application.usecase.product.commands.update

import java.util.*

data class UpdateProductCommand(
    val id: UUID,
    val name: String,
    val description: String?,
    val unitPrice: Double,
    val itemIds: List<UUID> = emptyList(),
    val updatedBy: UUID
)
