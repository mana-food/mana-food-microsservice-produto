package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.testutil.Fixtures
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.*

class ItemMapperExtraTest {
    @Test
    fun `toUpdateCommand should map request to command`() {
        val request = UpdateItemRequest(
            id = UUID.randomUUID(),
            name = "Item Y",
            description = "Desc Y",
            categoryId = UUID.randomUUID()
        )
        val updatedBy = UUID.randomUUID()
        val command = ItemMapper.toUpdateCommand(request, updatedBy)
        assertEquals(request.id, command.id)
        assertEquals(request.name, command.name)
        assertEquals(request.description, command.description)
        assertEquals(request.categoryId, command.categoryId)
        assertEquals(updatedBy, command.updatedBy)
    }

    @Test
    fun `toDeleteCommand should map id and deletedBy`() {
        val id = UUID.randomUUID()
        val deletedBy = UUID.randomUUID()
        val command = ItemMapper.toDeleteCommand(id, deletedBy)
        assertEquals(id, command.id)
        assertEquals(deletedBy, command.deletedBy)
    }

    @Test
    fun `toResponsePaged should map paged domain to paged response`() {
        val item = Fixtures.sampleItem()
        val paged = Paged(listOf(item), 0, 1, 1L, 1)
        val responsePaged = ItemMapper.toResponsePaged(paged)
        assertEquals(1, responsePaged.items.size)
        assertEquals(item.id, responsePaged.items[0].id)
        assertEquals(paged.totalItems, responsePaged.totalItems)
        assertEquals(paged.page, responsePaged.page)
        assertEquals(paged.pageSize, responsePaged.pageSize)
    }
}

