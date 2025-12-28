package br.com.manafood.manafoodproduct.adapter.request.commands.create

import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @field:NotBlank val name: String
)
