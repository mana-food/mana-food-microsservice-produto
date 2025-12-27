package br.com.manafood.manafoodpoduct.infrastructure.persistence.repository

import br.com.manafood.manafoodpoduct.infrastructure.persistence.entity.CategoryJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CategoryJpaRepository : JpaRepository<CategoryJpaEntity, UUID> {

    @Query("SELECT c FROM CategoryJpaEntity c")
    fun findPaged(pageable: Pageable): Page<CategoryJpaEntity>
}
