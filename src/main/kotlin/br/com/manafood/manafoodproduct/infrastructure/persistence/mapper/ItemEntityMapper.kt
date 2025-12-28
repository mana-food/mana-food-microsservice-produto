package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import org.springframework.data.domain.Page

object ItemEntityMapper {

    fun toEntity(domain: Item): ItemJpaEntity =
        ItemJpaEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            category = CategoryEntityMapper.toEntity(domain.category),
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            createdBy = domain.createdBy,
            updatedBy = domain.updatedBy,
            deleted = domain.deleted
        )

    fun toDomain(jpa: ItemJpaEntity): Item =
        Item(
            id = jpa.id,
            name = jpa.name,
            description = jpa.description,
            categoryId = jpa.category.id,
            category = CategoryEntityMapper.toDomain(jpa.category),
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt,
            createdBy = jpa.createdBy,
            updatedBy = jpa.updatedBy,
            deleted = jpa.deleted
        )

    fun toPagedDomain(jpa: Page<ItemJpaEntity>): Paged<Item> =
        Paged(
            items = jpa.content.map { toDomain(it) },
            page = jpa.number,
            pageSize = jpa.size,
            totalItems = jpa.totalElements,
            totalPages = jpa.totalPages
        )
}