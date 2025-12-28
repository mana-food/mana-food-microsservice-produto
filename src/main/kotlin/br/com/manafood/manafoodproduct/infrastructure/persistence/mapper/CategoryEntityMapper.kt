package br.com.manafood.manafoodproduct.infrastructure.persistence.mapper

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import org.springframework.data.domain.Page

object CategoryEntityMapper {

    fun toEntity(domain: Category): CategoryJpaEntity =
        CategoryJpaEntity(
            id = domain.id,
            name = domain.name,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            createdBy = domain.createdBy,
            updatedBy = domain.updatedBy,
            deleted = domain.deleted
        )

    fun toDomain(jpa: CategoryJpaEntity): Category  =
        Category(
            id = jpa.id,
            name = jpa.name,
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt,
            createdBy = jpa.createdBy,
            updatedBy = jpa.updatedBy,
            deleted = jpa.deleted
        )

    fun toPagedDomain(jpa: Page<CategoryJpaEntity>): Paged<Category> =
        Paged(
            items = jpa.content.map { toDomain(it) },
            page = jpa.number,
            pageSize = jpa.size,
            totalItems = jpa.totalElements,
            totalPages = jpa.totalPages
        )
}
