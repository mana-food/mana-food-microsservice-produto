package br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring

import br.com.manafood.manafoodpoduct.infrastructure.persistence.entity.ProductJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SpringProductJpaRepository : JpaRepository<ProductJpaEntity, UUID> {

    @Query("SELECT p FROM ProductJpaEntity p")
    fun findPaged(pageable: Pageable): Page<ProductJpaEntity>
}
