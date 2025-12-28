package br.com.manafood.manafoodproduct.adapter.request.commands.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class UpdateItemRequest(
    @field:NotNull val id: UUID,
    @field:NotBlank val name: String,
    val description: String?,
    @field:NotNull val categoryId: UUID
)
