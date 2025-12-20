package br.com.manafood.manafoodpoduct.adapter.request.commands.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class UpdateCategoryRequest(
    @field:NotNull val id: UUID,
    @field:NotBlank val name: String,
    val description: String?
)
