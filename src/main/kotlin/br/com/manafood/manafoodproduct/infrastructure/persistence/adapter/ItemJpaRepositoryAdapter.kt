package br.com.manafood.manafoodproduct.infrastructure.persistence.adapter

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import br.com.manafood.manafoodproduct.infrastructure.persistence.mapper.ItemEntityMapper
import br.com.manafood.manafoodproduct.infrastructure.persistence.repository.ItemJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ItemJpaRepositoryAdapter(
    private val springDataRepository: ItemJpaRepository
) : ItemRepository {

    override fun findPaged(
        page: Int,
        pageSize: Int
    ): Paged<Item> {
        val pageable = PageRequest.of(page, pageSize)
        val itemsPaged = springDataRepository.findPaged(pageable)

        return ItemEntityMapper.toPagedDomain(itemsPaged)
    }

    override fun findById(id: UUID): Item? {
        return springDataRepository.findByIdAndNotDeleted(id)
            .map { ItemEntityMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByIds(ids: List<UUID>): List<Item> {
        return springDataRepository.findAllByIdAndNotDeleted(ids)
            .map { ItemEntityMapper.toDomain(it) }
    }

    override fun save(entity: Item): Item {
        val saved = springDataRepository.save(
            ItemEntityMapper.toEntity(entity)
        )
        return ItemEntityMapper.toDomain(saved)
    }

    override fun deleteById(id: UUID): Boolean {
        springDataRepository.deleteById(id)

        val exists = springDataRepository.existsByIdAndNotDeleted(id)
        return !exists
    }
}
