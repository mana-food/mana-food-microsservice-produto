package br.com.manafood.manafoodpoduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.entity.ProductJpaEntity
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.ProductJpaRepositoryImpl
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductRepositoryImpl(
    private val jpaRepository: ProductJpaRepositoryImpl
) : ProductRepository {
    override fun findById(id: UUID): Product? {
        TODO("Not yet implemented")
    }

    override fun findPaged(
        page: Int,
        pageSize: Int
    ): Paged<Product> {
        TODO("Not yet implemented")
    }

    override fun findByIds(ids: List<UUID>): List<Product> {
        TODO("Not yet implemented")
    }

    override fun save(entity: Product): Product {
        return jpaRepository.save<ProductJpaEntity>(entity)
    }

    override fun deleteById(id: UUID): Boolean {
        TODO("Not yet implemented")
    }
}