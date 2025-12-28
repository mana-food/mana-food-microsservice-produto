package br.com.manafood.manafoodproduct.adapter.request.commands.create

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class CreateProductRequest(
    @field:NotBlank val name: String,
    val description: String?,
    @field:Positive val unitPrice: Double,
    @field:NotNull val categoryId: UUID,
    val itemIds: List<UUID> = emptyList()
)
