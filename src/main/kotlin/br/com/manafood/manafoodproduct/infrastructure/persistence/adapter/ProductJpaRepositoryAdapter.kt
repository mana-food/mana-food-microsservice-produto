package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Product
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.mapper.ProductEntityMapper
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ProductJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductJpaRepositoryAdapter(
    private val springDataRepository: ProductJpaRepository,
    private val productEntityMapper: ProductEntityMapper
) : ProductRepository {

    override fun findPaged(
        page: Int,
        pageSize: Int
    ): Paged<Product> {
        val pageable = PageRequest.of(page, pageSize)
        val productsPaged = springDataRepository.findPaged(pageable)

        return productEntityMapper.toPagedDomain(productsPaged)
    }

    override fun findById(id: UUID): Product? {
        return springDataRepository.findByIdAndNotDeleted(id)
            .map { productEntityMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByIds(ids: List<UUID>): List<Product> {
        return springDataRepository.findAllByIdAndNotDeleted(ids)
            .map { productEntityMapper.toDomain(it) }
    }

    override fun save(entity: Product): Product {
        val saved = springDataRepository.save(
            productEntityMapper.toEntity(entity)
        )
        return productEntityMapper.toDomain(saved)
    }

    override fun deleteById(id: UUID): Boolean {
        springDataRepository.deleteById(id)

        val exists = springDataRepository.existsByIdAndNotDeleted(id)
        return !exists
    }
}
