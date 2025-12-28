package br.com.manafood.manafoodproduct.application.usecase.product.commands.update

import java.math.BigDecimal
import java.util.*

data class UpdateProductCommand(
    val id: UUID,
    val name: String,
    val description: String?,
    val unitPrice: BigDecimal,
    val itemIds: List<UUID> = emptyList(),
    val categoryId: UUID,
    val updatedBy: UUID
)
