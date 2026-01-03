package br.com.manafood.manafoodproduct.infrastructure.persistence.repository

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface ProductJpaRepository : JpaRepository<ProductJpaEntity, UUID> {

    @Query("SELECT p FROM ProductJpaEntity p WHERE p.deleted = false")
    fun findPaged(pageable: Pageable): Page<ProductJpaEntity>

    @Query("SELECT p FROM ProductJpaEntity p WHERE p.id = :id AND p.deleted = false")
    fun findByIdAndNotDeleted(id: UUID): Optional<ProductJpaEntity>

    @Query("SELECT p FROM ProductJpaEntity p WHERE p.id IN :ids AND p.deleted = false")
    fun findAllByIdAndNotDeleted(ids: List<UUID>): List<ProductJpaEntity>

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM ProductJpaEntity p WHERE p.id = :id AND p.deleted = false")
    fun existsByIdAndNotDeleted(id: UUID): Boolean
}
