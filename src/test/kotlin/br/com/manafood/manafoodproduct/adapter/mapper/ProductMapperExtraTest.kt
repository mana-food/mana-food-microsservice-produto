package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Product
import br.com.manafood.manafoodproduct.testutil.Fixtures
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.*

class ProductMapperExtraTest {
    @Test
    fun `toUpdateCommand should map request to command`() {
        val request = UpdateProductRequest(
            id = UUID.randomUUID(),
            name = "Produto Y",
            description = "Desc Y",
            unitPrice = 22.5,
            categoryId = UUID.randomUUID(),
            itemIds = emptyList()
        )
        val updatedBy = UUID.randomUUID()
        val command = ProductMapper.toUpdateCommand(request, updatedBy)
        assertEquals(request.id, command.id)
        assertEquals(request.name, command.name)
        assertEquals(request.description, command.description)
        assertEquals(request.unitPrice, command.unitPrice)
        assertEquals(request.categoryId, command.categoryId)
        assertEquals(updatedBy, command.updatedBy)
    }

    @Test
    fun `toDeleteCommand should map id and deletedBy`() {
        val id = UUID.randomUUID()
        val deletedBy = UUID.randomUUID()
        val command = ProductMapper.toDeleteCommand(id, deletedBy)
        assertEquals(id, command.id)
        assertEquals(deletedBy, command.deletedBy)
    }

    @Test
    fun `toResponsePaged should map paged domain to paged response`() {
        val product = Fixtures.sampleProduct()
        val paged = Paged(listOf(product), 0, 1, 1L, 1)
        val responsePaged = ProductMapper.toResponsePaged(paged)
        assertEquals(1, responsePaged.items.size)
        assertEquals(product.id, responsePaged.items[0].id)
        assertEquals(paged.totalItems, responsePaged.totalItems)
        assertEquals(paged.page, responsePaged.page)
        assertEquals(paged.pageSize, responsePaged.pageSize)
    }
}

