package br.com.manafood.manafoodpoduct.infrastructure.persistence.repository

import br.com.manafood.manafoodpoduct.domain.common.Paged
import br.com.manafood.manafoodpoduct.domain.model.Item
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.infrastructure.persistence.mapper.ItemEntityMapper
import br.com.manafood.manafoodpoduct.infrastructure.persistence.repository.spring.SpringItemJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ItemJpaRepositoryImpl(
    private val springDataRepository: SpringItemJpaRepository
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
        return springDataRepository.findById(id)
            .map { ItemEntityMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByIds(ids: List<UUID>): List<Item> {
        return springDataRepository.findAllById(ids)
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

        val exists = springDataRepository.existsById(id)
        return !exists
    }
}
