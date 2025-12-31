package br.com.manafood.manafoodproduct.application.usecase.product.commands.create

import java.math.BigDecimal
import java.util.*

data class CreateProductCommand(
    val name: String,
    val description: String?,
    val unitPrice: BigDecimal,
    val categoryId: UUID,
    val itemIds: List<UUID> = emptyList(),
    val createdBy: UUID
)