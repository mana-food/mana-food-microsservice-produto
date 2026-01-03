package br.com.manafood.manafoodproduct.infrastructure.persistence.repository

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface ItemJpaRepository : JpaRepository<ItemJpaEntity, UUID> {

    @Query("SELECT i FROM ItemJpaEntity i WHERE i.deleted = false")
    fun findPaged(pageable: Pageable): Page<ItemJpaEntity>

    @Query("SELECT i FROM ItemJpaEntity i WHERE i.id = :id AND i.deleted = false")
    fun findByIdAndNotDeleted(id: UUID): Optional<ItemJpaEntity>

    @Query("SELECT i FROM ItemJpaEntity i WHERE i.id IN :ids AND i.deleted = false")
    fun findAllByIdAndNotDeleted(ids: List<UUID>): List<ItemJpaEntity>

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM ItemJpaEntity i WHERE i.id = :id AND i.deleted = false")
    fun existsByIdAndNotDeleted(id: UUID): Boolean
}
