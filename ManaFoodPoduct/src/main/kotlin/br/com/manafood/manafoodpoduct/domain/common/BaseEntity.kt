package br.com.manafood.manafoodpoduct.domain.common

import java.time.LocalDateTime
import java.util.UUID

abstract class BaseEntity(
    open val id: UUID? = null,
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    open val createdBy: UUID,
    open val updatedAt: LocalDateTime? = null,
    open val updatedBy: UUID? = null,
    open val deleted: Boolean = false
)
