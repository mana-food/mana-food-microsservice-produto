package br.com.manafood.manafoodpoduct.domain.repository

import br.com.manafood.manafoodpoduct.domain.common.BaseEntity
import br.com.manafood.manafoodpoduct.domain.common.Paged
import java.util.*

interface BaseRepository<T : BaseEntity> {

    suspend fun getAll(): List<T>
    suspend fun getAllPaged(page: Int, pageSize: Int): Paged<T>
    suspend fun getByIds(ids: List<UUID>): List<T>
    suspend fun getBy(predicate: (T) -> Boolean): T?
    suspend fun create(entity: T): T
    suspend fun update(entity: T): T
    suspend fun delete(entity: T)
}
