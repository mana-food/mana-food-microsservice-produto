package br.com.manafood.manafoodpoduct.domain.model

import br.com.manafood.manafoodpoduct.domain.common.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

data class Product(
    override val id: UUID,
    override val createdBy: UUID,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedBy: UUID? = null,
    override val updatedAt: LocalDateTime? = null,
    override val deleted: Boolean = false,
    val name: String,
    val description: String,
    val unitPrice: Double,
    val categoryId: UUID,
    val category: Category,
    val items: MutableList<Item> = mutableListOf()
) : BaseEntity(
    id = id,
    createdBy = createdBy,
    createdAt = createdAt,
    updatedBy = updatedBy,
    updatedAt = updatedAt,
    deleted = deleted
)
