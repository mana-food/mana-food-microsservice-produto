package br.com.manafood.manafoodpoduct.domain.model

import br.com.manafood.manafoodpoduct.domain.common.BaseEntity
import java.util.*

class Item(
    override val id: UUID? = null,
    override val createdBy: UUID,
    override val updatedBy: UUID? = null,
    override val deleted: Boolean = false,
    val name: String,
    val description: String,
    val categoryId: UUID,
    val category: Category
) : BaseEntity(
    id = id,
    createdBy = createdBy,
    updatedBy = updatedBy,
    deleted = deleted
)
