package br.com.manafood.manafoodpoduct.adapter.request.commands.create

import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @field:NotBlank val name: String
)
