package br.com.manafood.manafoodproduct.application.usecase.category.commands.create

import java.util.*

data class CreateCategoryCommand(
    val name: String,
    val createdBy: UUID
)
