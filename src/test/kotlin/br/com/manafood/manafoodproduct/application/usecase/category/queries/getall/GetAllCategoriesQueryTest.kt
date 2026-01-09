package br.com.manafood.manafoodproduct.application.usecase.category.queries.getall

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllCategoriesQueryTest {

    @Test
    fun `should create query with default values`() {
        // When
        val query = GetAllCategoriesQuery()

        // Then
        assertEquals(0, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with custom values`() {
        // When
        val query = GetAllCategoriesQuery(page = 2, pageSize = 20)

        // Then
        assertEquals(2, query.page)
        assertEquals(20, query.pageSize)
    }

    @Test
    fun `should create query with only page parameter`() {
        // When
        val query = GetAllCategoriesQuery(page = 5)

        // Then
        assertEquals(5, query.page)
        assertEquals(10, query.pageSize)
    }

    @Test
    fun `should create query with only pageSize parameter`() {
        // When
        val query = GetAllCategoriesQuery(pageSize = 50)

        // Then
        assertEquals(0, query.page)
        assertEquals(50, query.pageSize)
    }
}

