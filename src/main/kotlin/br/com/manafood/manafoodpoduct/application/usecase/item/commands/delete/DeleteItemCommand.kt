package br.com.manafood.manafoodpoduct.application.usecase.item.commands.delete

import java.util.*

data class DeleteItemCommand(
    val id: UUID,
    val deletedBy: UUID
)
