package br.com.manafood.manafoodpoduct.application.usecase.item.commands.create

import java.util.*

data class CreateItemCommand(
    val name: String,
    val description: String,
    val categoryId: UUID,
    val createdBy: UUID
)
