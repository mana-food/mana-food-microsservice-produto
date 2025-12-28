package br.com.manafood.manafoodproduct.adapter.request.commands.delete

import jakarta.validation.constraints.NotNull
import java.util.*

data class DeleteProductRequest(
    @field:NotNull val id: UUID
)
