package br.com.manafood.manafoodpoduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.infrastructure.persistence.entity.ProductJpaEntity
import org.springframework.data.domain.Page

object ProductEntityMapper {

    fun toEntity(domain: Product): ProductJpaEntity =
        ProductJpaEntity(
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

    fun toDomain(jpa: ProductJpaEntity): Product =
        Product(
            id = jpa.id,
            name = jpa.name,
            description = jpa.description ?: "",
            unitPrice = jpa.unitPrice,
            categoryId = jpa.category.id,
            category = CategoryEntityMapper.toDomain(jpa.category),
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt,
            createdBy = jpa.createdBy,
            updatedBy = jpa.updatedBy,
            deleted = jpa.deleted
        )

    fun toPagedDomain(jpa: Page<ProductJpaEntity>): Paged<Product> =
        Paged(
            items = jpa.content.map { toDomain(it) },
            page = jpa.number,
            pageSize = jpa.size,
            totalItems = jpa.totalElements,
            totalPages = jpa.totalPages
        )
}