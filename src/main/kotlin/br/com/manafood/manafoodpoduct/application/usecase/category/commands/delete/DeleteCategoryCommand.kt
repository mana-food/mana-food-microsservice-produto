package br.com.manafood.manafoodpoduct.application.usecase.category.commands.delete

import java.util.*

data class DeleteCategoryCommand(
    val id: UUID,
    val deletedBy: UUID
)
