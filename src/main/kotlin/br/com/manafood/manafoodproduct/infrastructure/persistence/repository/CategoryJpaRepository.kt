package br.com.manafood.manafoodproduct.infrastructure.persistence.repository

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface CategoryJpaRepository : JpaRepository<CategoryJpaEntity, UUID> {

    @Query("SELECT c FROM CategoryJpaEntity c WHERE c.deleted = false")
    fun findPaged(pageable: Pageable): Page<CategoryJpaEntity>

    @Query("SELECT c FROM CategoryJpaEntity c WHERE c.id = :id AND c.deleted = false")
    fun findByIdAndNotDeleted(id: UUID): Optional<CategoryJpaEntity>

    @Query("SELECT c FROM CategoryJpaEntity c WHERE c.id IN :ids AND c.deleted = false")
    fun findAllByIdAndNotDeleted(ids: List<UUID>): List<CategoryJpaEntity>

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CategoryJpaEntity c WHERE c.id = :id AND c.deleted = false")
    fun existsByIdAndNotDeleted(id: UUID): Boolean
}
