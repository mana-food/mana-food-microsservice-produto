package br.com.manafood.manafoodpoduct.application.usecase.product.commands.delete

import java.util.*

data class DeleteProductCommand(
    val id: UUID,
    val deletedBy: UUID
)
