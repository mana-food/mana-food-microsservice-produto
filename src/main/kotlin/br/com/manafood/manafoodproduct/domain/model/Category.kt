package br.com.manafood.manafoodproduct.domain.model

import br.com.manafood.manafoodproduct.domain.common.BaseEntity
import java.time.LocalDateTime
import java.util.*

data class Category(
    override val id: UUID,
    override val createdBy: UUID,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedBy: UUID? = null,
    override val updatedAt: LocalDateTime? = null,
    override val deleted: Boolean = false,
    val name: String
) : BaseEntity(
    id = id,
    createdBy = createdBy,
    createdAt = createdAt,
    updatedBy = updatedBy,
    updatedAt = updatedAt,
    deleted = deleted
)
