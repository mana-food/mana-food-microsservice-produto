package br.com.manafood.manafoodproduct.adapter.request.commands.create

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class CreateItemRequest(
    @field:NotBlank val name: String,
    val description: String?,
    @field:NotNull val categoryId: UUID
)
