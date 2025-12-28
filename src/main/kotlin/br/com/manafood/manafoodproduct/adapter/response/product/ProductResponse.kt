package br.com.manafood.manafoodproduct.adapter.response.product

import br.com.manafood.manafoodproduct.adapter.response.common.CategorySummaryResponse
import java.time.LocalDateTime
import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val unitPrice: Double,
    val category: CategorySummaryResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)
