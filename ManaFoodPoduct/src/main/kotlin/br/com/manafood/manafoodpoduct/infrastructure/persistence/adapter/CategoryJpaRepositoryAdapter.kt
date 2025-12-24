package br.com.manafood.manafoodpoduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Category
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.mapper.CategoryEntityMapper
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.CategoryJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CategoryJpaRepositoryAdapter(
    private val springDataRepository: CategoryJpaRepository
) : CategoryRepository {

    override fun findPaged(
        page: Int,
        pageSize: Int
    ): Paged<Category> {
        val pageable = PageRequest.of(page, pageSize)
        val categoriesPaged = springDataRepository.findPaged(pageable)

        return CategoryEntityMapper.toPagedDomain(categoriesPaged)
    }

    override fun findById(id: UUID): Category? {
        return springDataRepository.findById(id)
            .map { CategoryEntityMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByIds(ids: List<UUID>): List<Category> {
        return springDataRepository.findAllById(ids)
            .map { CategoryEntityMapper.toDomain(it) }
    }

    override fun save(entity: Category): Category {
        val saved = springDataRepository.save(
            CategoryEntityMapper.toEntity(entity)
        )
        return CategoryEntityMapper.toDomain(saved)
    }

    override fun deleteById(id: UUID): Boolean {
        springDataRepository.deleteById(id)

        val exists = springDataRepository.existsById(id)
        return !exists
    }
}
