package br.com.manafood.manafoodpoduct.infrastructure.persistence.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "product")
class ProductEntity(

    id: UUID,

    @Column(nullable = false)
    val name: String,

    @Column
    val description: String? = null,

    @Column(nullable = false)
    val unitPrice: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
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
