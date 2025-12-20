package br.com.manafood.manafoodpoduct.domain.model

import br.com.manafood.manafoodpoduct.domain.common.BaseEntity
import java.util.UUID

data class Product(
    override val id: UUID? = null,
    override val createdBy: UUID,
    override val updatedBy: UUID? = null,
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
    updatedBy = updatedBy,
    deleted = deleted
)
