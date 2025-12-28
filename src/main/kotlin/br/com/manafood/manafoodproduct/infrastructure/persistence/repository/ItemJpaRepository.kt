package br.com.manafood.manafoodproduct.infrastructure.persistence.repository

import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ItemJpaRepository : JpaRepository<ItemJpaEntity, UUID> {

    @Query("SELECT i FROM ItemJpaEntity i")
    fun findPaged(pageable: Pageable): Page<ItemJpaEntity>
}
