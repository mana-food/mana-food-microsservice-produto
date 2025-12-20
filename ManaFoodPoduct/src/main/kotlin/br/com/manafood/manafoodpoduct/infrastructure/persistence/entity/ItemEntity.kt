package br.com.manafood.manafoodpoduct.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "item")
class ItemEntity(

    id: UUID,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableList<ProductItemEntity> = mutableListOf(),

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
