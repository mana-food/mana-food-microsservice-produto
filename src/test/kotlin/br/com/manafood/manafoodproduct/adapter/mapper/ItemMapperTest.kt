package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.testutil.Fixtures
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemMapperTest {

    @Test
    fun `toResponse maps item correctly`() {
        val item = Fixtures.sampleItem()
        val response = ItemMapper.toResponse(item)

        assertEquals(item.id, response.id)
        assertEquals(item.name, response.name)
    }
}

