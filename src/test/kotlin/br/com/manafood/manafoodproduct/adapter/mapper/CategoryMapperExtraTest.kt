package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.testutil.Fixtures
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.*

class CategoryMapperExtraTest {
    @Test
    fun `toUpdateCommand should map request to command`() {
        val request = UpdateCategoryRequest(
            id = UUID.randomUUID(),
            name = "Cat Y"
        )
        val updatedBy = UUID.randomUUID()
        val command = CategoryMapper.toUpdateCommand(request, updatedBy)
        assertEquals(request.id, command.id)
        assertEquals(request.name, command.name)
        assertEquals(updatedBy, command.updatedBy)
    }

    @Test
    fun `toDeleteCommand should map id and deletedBy`() {
        val id = UUID.randomUUID()
        val deletedBy = UUID.randomUUID()
        val command = CategoryMapper.toDeleteCommand(id, deletedBy)
        assertEquals(id, command.id)
        assertEquals(deletedBy, command.deletedBy)
    }

    @Test
    fun `toResponsePaged should map paged domain to paged response`() {
        val category = Fixtures.sampleCategory()
        val paged = Paged(listOf(category), 0, 1, 1L, 1)
        val responsePaged = CategoryMapper.toResponsePaged(paged)
        assertEquals(1, responsePaged.items.size)
        assertEquals(category.id, responsePaged.items[0].id)
        assertEquals(paged.totalItems, responsePaged.totalItems)
        assertEquals(paged.page, responsePaged.page)
        assertEquals(paged.pageSize, responsePaged.pageSize)
    }
}

