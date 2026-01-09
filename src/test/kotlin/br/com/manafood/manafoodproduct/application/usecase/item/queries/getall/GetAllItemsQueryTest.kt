package br.com.manafood.manafoodproduct.application.usecase.item.queries.getall

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllItemsQueryTest {

    @Test
    fun `should create query with default values`() {
        // When
        val query = GetAllItemsQuery()

        // Then
        assertEquals(0, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with custom values`() {
        // When
        val query = GetAllItemsQuery(page = 3, pageSize = 25)

        // Then
        assertEquals(3, query.page)
        assertEquals(25, query.pageSize)
    }

    @Test
    fun `should create query with only page parameter`() {
        // When
        val query = GetAllItemsQuery(page = 7)

        // Then
        assertEquals(7, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with only pageSize parameter`() {
        // When
        val query = GetAllItemsQuery(pageSize = 100)

        // Then
        assertEquals(0, query.page)
        assertEquals(100, query.pageSize)
    }
}

