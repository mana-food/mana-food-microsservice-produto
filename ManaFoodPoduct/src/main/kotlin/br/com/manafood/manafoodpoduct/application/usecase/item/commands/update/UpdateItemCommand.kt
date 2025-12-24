package br.com.manafood.manafoodpoduct.application.usecase.item.commands.update

import java.util.*

data class UpdateItemCommand(
    val id: UUID,
    val name: String,
    val description: String,
    val categoryId: UUID,
    val updatedBy: UUID
)
