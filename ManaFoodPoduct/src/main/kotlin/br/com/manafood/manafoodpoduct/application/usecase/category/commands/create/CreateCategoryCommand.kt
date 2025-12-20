package br.com.manafood.manafoodpoduct.application.usecase.category.commands.create

import java.util.*

data class CreateCategoryCommand(
    val name: String,
    val description: String?,
    val createdBy: UUID
)
