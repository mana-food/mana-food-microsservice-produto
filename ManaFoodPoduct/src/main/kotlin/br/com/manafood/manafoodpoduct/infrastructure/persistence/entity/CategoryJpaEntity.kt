package br.com.manafood.manafoodpoduct.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "category")
class CategoryJpaEntity(

    id: UUID,

    @Column(nullable = false, unique = true)
    val name: String,

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val products: MutableList<ProductJpaEntity> = mutableListOf(),

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val items: MutableList<ItemJpaEntity> = mutableListOf(),

    createdAt: LocalDateTime,
    createdBy: UUID,
    updatedAt: LocalDateTime? = null,
    updatedBy: UUID? = null,
    deleted: Boolean = false

) : BaseJpaEntity(
    id = id,
    createdAt = createdAt,
    createdBy = createdBy,
    updatedAt = updatedAt,
    updatedBy = updatedBy,
    deleted = deleted
)
