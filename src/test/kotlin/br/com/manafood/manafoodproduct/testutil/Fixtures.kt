package br.com.manafood.manafoodproduct.testutil

import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.domain.model.Product
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.CategoryJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ItemJpaEntity
import br.com.manafood.manafoodproduct.infrastructure.persistence.entity.ProductJpaEntity
import java.time.LocalDateTime
import java.util.*

object Fixtures {

    fun sampleCategory(id: UUID = UUID.randomUUID(), name: String = "Categoria A") =
        Category(
            id = id,
            createdBy = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedBy = null,
            updatedAt = null,
            deleted = false,
            name = name
        )

    fun sampleItem(id: UUID = UUID.randomUUID(), name: String = "Item A") =
        Item(
            id = id,
            createdBy = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedBy = null,
            updatedAt = null,
            deleted = false,
            name = name,
            description = null,
            categoryId = UUID.randomUUID(),
            category = sampleCategory()
        )

    fun sampleProduct(id: UUID = UUID.randomUUID()): Product =
        Product(
            id = id,
            createdBy = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedBy = null,
            updatedAt = null,
            deleted = false,
            name = "Produto A",
            description = "Descricao",
            unitPrice = 10.0,
            categoryId = UUID.randomUUID(),
            category = sampleCategory(),
            items = mutableListOf(sampleItem())
        )

    fun sampleCategoryJpaEntity(id: UUID = UUID.randomUUID(), name: String = "Categoria A") =
        CategoryJpaEntity(
            id = id,
            name = name,
            products = mutableListOf(),
            items = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = null,
            updatedBy = null,
            deleted = false
        )

    fun sampleItemJpaEntity(id: UUID = UUID.randomUUID(), name: String = "Item A") =
        ItemJpaEntity(
            id = id,
            name = name,
            description = null,
            category = sampleCategoryJpaEntity(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = null,
            updatedBy = null,
            deleted = false
        )

    fun sampleProductJpaEntity(id: UUID = UUID.randomUUID()) =
        ProductJpaEntity(
            id = id,
            name = "Produto A",
            description = "Descricao",
            unitPrice = 10.0,
            category = sampleCategoryJpaEntity(),
            productItems = mutableListOf(),
            createdAt = LocalDateTime.now(),
            createdBy = UUID.randomUUID(),
            updatedAt = null,
            updatedBy = null,
            deleted = false
        )
}
