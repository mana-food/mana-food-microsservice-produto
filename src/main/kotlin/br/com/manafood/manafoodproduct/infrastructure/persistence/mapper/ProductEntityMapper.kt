package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Product
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class ProductEntityMapper(
    private val itemJpaRepository: ItemJpaRepository
) {

    fun toEntity(domain: Product): ProductJpaEntity {
        val productEntity = ProductJpaEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            unitPrice = domain.unitPrice,
            category = CategoryEntityMapper.toEntity(domain.category),
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            createdBy = domain.createdBy,
            updatedBy = domain.updatedBy,
            deleted = domain.deleted
        )

        if (domain.items.isNotEmpty()) {
            val itemIds = domain.items.map { it.id }
            val itemEntities = itemJpaRepository.findAllByIdAndNotDeleted(itemIds)
            productEntity.syncItems(itemEntities, domain.createdBy)
        }

        return productEntity
    }

    fun toDomain(jpa: ProductJpaEntity): Product {
        val items = jpa.getItems()
            .map { ItemEntityMapper.toDomain(it) }
            .toMutableList()

        return Product(
            id = jpa.id,
            name = jpa.name,
            description = jpa.description ?: "",
            unitPrice = jpa.unitPrice,
            categoryId = jpa.category.id,
            category = CategoryEntityMapper.toDomain(jpa.category),
            items = items,
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt,
            createdBy = jpa.createdBy,
            updatedBy = jpa.updatedBy,
            deleted = jpa.deleted
        )
    }

    fun toPagedDomain(jpa: Page<ProductJpaEntity>): Paged<Product> =
        Paged(
            items = jpa.content.map { toDomain(it) },
            page = jpa.number,
            pageSize = jpa.size,
            totalItems = jpa.totalElements,
            totalPages = jpa.totalPages
        )
}
