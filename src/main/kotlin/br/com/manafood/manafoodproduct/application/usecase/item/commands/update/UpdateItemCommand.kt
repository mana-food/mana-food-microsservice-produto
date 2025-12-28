package br.com.manafood.manafoodproduct.application.usecase.item.commands.update

import java.util.*

data class UpdateItemCommand(
    val id: UUID,
    val name: String,
    val description: String?,
    val categoryId: UUID,
    val updatedBy: UUID
)
