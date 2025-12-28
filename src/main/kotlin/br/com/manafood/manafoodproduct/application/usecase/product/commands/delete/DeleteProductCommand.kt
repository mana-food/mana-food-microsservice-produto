package br.com.manafood.manafoodproduct.application.usecase.product.commands.delete

import java.util.*

data class DeleteProductCommand(
    val id: UUID,
    val deletedBy: UUID
)
