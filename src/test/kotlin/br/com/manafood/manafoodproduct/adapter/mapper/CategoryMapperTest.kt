package br.com.manafood.manafoodproduct.adapter.mapper

import br.com.manafood.manafoodproduct.testutil.Fixtures
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryMapperTest {

    @Test
    fun `toResponse maps category correctly`() {
        val category = Fixtures.sampleCategory()
        val response = CategoryMapper.toResponse(category)

        assertEquals(category.id, response.id)
        assertEquals(category.name, response.name)
    }
}

