package br.com.manafood.manafoodpoduct.infrastructure.persistence.repository

import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.mapper.ProductEntityMapper
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring.SpringProductJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductJpaRepositoryImpl(
    private val springDataRepository: SpringProductJpaRepository
) : ProductRepository {

    override fun findPaged(
        page: Int,
        pageSize: Int
    ): Paged<Product> {
        val pageable = PageRequest.of(page, pageSize)
        val productsPaged = springDataRepository.findPaged(pageable)

        return ProductEntityMapper.toPagedDomain(productsPaged)
    }

    override fun findById(id: UUID): Product? {
        return springDataRepository.findById(id)
            .map { ProductEntityMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByIds(ids: List<UUID>): List<Product> {
        return springDataRepository.findAllById(ids)
            .map { ProductEntityMapper.toDomain(it) }
    }

    override fun save(entity: Product): Product {
        val saved = springDataRepository.save(
            ProductEntityMapper.toEntity(entity)
        )
        return ProductEntityMapper.toDomain(saved)
    }

    override fun deleteById(id: UUID): Boolean {
        springDataRepository.deleteById(id)

        val exists = springDataRepository.existsById(id)
        return !exists
    }
}
