package br.com.manafood.manafoodproduct.infrastructure.persistence.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "product")
class ProductJpaEntity(

    id: UUID,

    @Column(nullable = false)
    val name: String,

    @Column
    val description: String? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    val unitPrice: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryJpaEntity,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val productItems: MutableList<ProductItemJpaEntity> = mutableListOf(),

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
) {

    fun syncItems(items: List<ItemJpaEntity>, userId: UUID) {
        productItems.clear()
        items.forEach { item ->
            val productItem = ProductItemJpaEntity(
                id = UUID.randomUUID(),
                product = this,
                item = item,
                createdAt = LocalDateTime.now(),
                createdBy = userId,
                deleted = false
            )
            productItems.add(productItem)
        }
    }

    fun getItems(): List<ItemJpaEntity> {
        return productItems.filter { !it.deleted }.map { it.item }
    }
}


