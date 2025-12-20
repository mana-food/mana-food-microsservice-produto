package br.com.manafood.manafoodpoduct.adapter.request.commands.delete

import jakarta.validation.constraints.NotNull
import java.util.*

data class DeleteItemRequest(
    @field:NotNull val id: UUID
)
