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
    var name: String,

    @Column
    var description: String? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    var unitPrice: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: CategoryJpaEntity,

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
        val newItemIds = items.map { it.id }.toSet()

        // Soft delete items that are no longer in the list
        productItems.forEach { productItem ->
            if (!productItem.deleted && !newItemIds.contains(productItem.item.id)) {
                productItem.deleted = true
                productItem.updatedAt = LocalDateTime.now()
                productItem.updatedBy = userId
            }
        }

        // Reactivate or add new items
        items.forEach { item ->
            val existing = productItems.find { it.item.id == item.id }
            if (existing != null) {
                // Reactivate if it was deleted
                if (existing.deleted) {
                    existing.deleted = false
                    existing.updatedAt = LocalDateTime.now()
                    existing.updatedBy = userId
                }
            } else {
                // Create new association
                productItems.add(
                    ProductItemJpaEntity(
                        id = UUID.randomUUID(),
                        product = this,
                        item = item,
                        createdAt = LocalDateTime.now(),
                        createdBy = userId,
                        deleted = false
                    )
                )
            }
        }
    }

    fun getItems(): List<ItemJpaEntity> {
        return productItems.filter { !it.deleted }.map { it.item }
    }
}


