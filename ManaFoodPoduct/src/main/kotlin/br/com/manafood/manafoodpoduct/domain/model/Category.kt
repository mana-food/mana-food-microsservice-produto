package br.com.manafood.manafoodpoduct.domain.model

import br.com.manafood.manafoodpoduct.domain.common.BaseEntity
import java.util.*

data class Category(
    override val id: UUID? = null,
    override val createdBy: UUID,
    override val updatedBy: UUID? = null,
    override val deleted: Boolean = false,
    val name: String
) : BaseEntity(
    id = id,
    createdBy = createdBy,
    updatedBy = updatedBy,
    deleted = deleted
)
