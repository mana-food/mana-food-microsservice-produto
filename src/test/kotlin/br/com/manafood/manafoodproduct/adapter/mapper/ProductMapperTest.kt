package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.testutil.Fixtures
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import java.util.*

class ProductMapperTest {

    @Test
    fun `toResponse should map domain product to response`() {
        val product = Fixtures.sampleProduct()

        val response = ProductMapper.toResponse(product)

        assertNotNull(response)
        assertEquals(product.id, response.id)
        assertEquals(product.name, response.name)
        assertEquals(product.description, response.description)
        assertEquals(product.unitPrice, response.unitPrice)
        assertEquals(product.category.id, response.category.id)
        assertEquals(product.category.name, response.category.name)
    }

    @Test
    fun `toCreateCommand should map request to command`() {
        val request = br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateProductRequest(
            name = "Produto X",
            description = "Desc",
            unitPrice = BigDecimal("12.5"),
            categoryId = UUID.randomUUID(),
            itemIds = emptyList()
        )

        val command = ProductMapper.toCreateCommand(request, UUID.randomUUID())

        assertEquals(request.name, command.name)
        assertEquals(request.description, command.description)
        assertEquals(request.unitPrice, command.unitPrice)
        assertEquals(request.categoryId, command.categoryId)
    }
}

