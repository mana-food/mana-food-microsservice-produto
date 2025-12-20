package br.com.manafood.manafoodpoduct.adapter.request.commands.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class UpdateProductRequest(
    @field:NotNull val id: UUID,
    @field:NotBlank val name: String,
    val description: String?,
    @field:Positive val unitPrice: Double,
    val itemIds: List<UUID> = emptyList()
)
