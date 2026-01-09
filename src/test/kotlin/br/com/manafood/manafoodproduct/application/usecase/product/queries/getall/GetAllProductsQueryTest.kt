package br.com.manafood.manafoodproduct.application.usecase.product.queries.getall

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllProductsQueryTest {

    @Test
    fun `should create query with default values`() {
        // When
        val query = GetAllProductsQuery()

        // Then
        assertEquals(0, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with custom values`() {
        // When
        val query = GetAllProductsQuery(page = 1, pageSize = 15)

        // Then
        assertEquals(1, query.page)
        assertEquals(15, query.pageSize)
    }

    @Test
    fun `should create query with only page parameter`() {
        // When
        val query = GetAllProductsQuery(page = 4)

        // Then
        assertEquals(4, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with only pageSize parameter`() {
        // When
        val query = GetAllProductsQuery(pageSize = 30)

        // Then
        assertEquals(0, query.page)
        assertEquals(30, query.pageSize)
    }
}

