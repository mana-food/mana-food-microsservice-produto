package br.com.manafood.manafoodpoduct.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "category")
class CategoryEntity(

    id: UUID,

    @Column(nullable = false, unique = true)
    val name: String,

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val products: MutableList<ProductEntity> = mutableListOf(),

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val items: MutableList<ItemEntity> = mutableListOf(),

    createdAt: LocalDateTime,
    createdBy: UUID,
    updatedAt: LocalDateTime? = null,
    updatedBy: UUID? = null,
    deleted: Boolean = false

) : BaseEntity(
    id = id,
    createdAt = createdAt,
    createdBy = createdBy,
    updatedAt = updatedAt,
    updatedBy = updatedBy,
    deleted = deleted
)
