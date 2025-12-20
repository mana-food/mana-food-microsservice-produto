package br.com.manafood.manafoodpoduct.adapter.request.commands.create

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class CreateItemRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val description: String,
    @field:NotNull val categoryId: UUID
)
