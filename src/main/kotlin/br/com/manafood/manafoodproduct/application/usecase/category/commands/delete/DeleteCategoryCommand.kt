package br.com.manafood.manafoodproduct.application.usecase.category.commands.delete

import java.util.*

data class DeleteCategoryCommand(
    val id: UUID,
    val deletedBy: UUID
)
