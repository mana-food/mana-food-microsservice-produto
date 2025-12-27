package br.com.manafood.manafoodpoduct.adapter.response.item

import br.com.manafood.manafoodpoduct.adapter.response.common.CategorySummaryResponse
import java.time.LocalDateTime
import java.util.UUID

data class ItemResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val category: CategorySummaryResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)
