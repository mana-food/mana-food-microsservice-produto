package br.com.manafood.manafoodproduct.application.usecase.category.commands.update

import java.util.*

data class UpdateCategoryCommand(
    val id: UUID,
    val name: String,
    val updatedBy: UUID
)
