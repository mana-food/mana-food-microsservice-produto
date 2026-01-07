package br.com.manafood.manafoodproduct.adapter.controller

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodproduct.application.usecase.category.commands.create.CreateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.delete.DeleteCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.update.UpdateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesQuery
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdQuery
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdUseCase
import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.testutil.Fixtures
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(CategoryController::class)
class CategoryControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var createCategoryUseCase: CreateCategoryUseCase

    @MockkBean
    lateinit var updateCategoryUseCase: UpdateCategoryUseCase

    @MockkBean
    lateinit var deleteCategoryUseCase: DeleteCategoryUseCase

    @MockkBean
    lateinit var getCategoryByIdUseCase: GetCategoryByIdUseCase

    @MockkBean
    lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase

    private val mapper = jacksonObjectMapper()

    @Test
    fun `create should return 200 with category when created successfully`() {
        // Given
        val request = CreateCategoryRequest(name = "Test Category")
        val category = Fixtures.sampleCategory()

        every { createCategoryUseCase.execute(any()) } returns category

        // When & Then
        mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(category.id.toString()))
            .andExpect(jsonPath("$.name").value(category.name))
    }

    @Test
    fun `update should return 200 with updated category`() {
        // Given
        val categoryId = UUID.randomUUID()
        val request = UpdateCategoryRequest(
            id = categoryId,
            name = "Updated Category"
        )
        val updatedCategory = Fixtures.sampleCategory().copy(id = categoryId, name = "Updated Category")

        every { updateCategoryUseCase.execute(any()) } returns updatedCategory

        // When & Then
        mockMvc.perform(
            put("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(categoryId.toString()))
            .andExpect(jsonPath("$.name").value("Updated Category"))
    }

    @Test
    fun `delete should return 204 when deleted successfully`() {
        // Given
        val id = UUID.randomUUID()

        justRun { deleteCategoryUseCase.execute(any()) }

        // When & Then
        mockMvc.perform(delete("/api/categories/$id"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `getById should return 200 with category when found`() {
        // Given
        val category = Fixtures.sampleCategory()
        every { getCategoryByIdUseCase.execute(GetCategoryByIdQuery(category.id!!)) } returns category

        // When & Then
        mockMvc.perform(get("/api/categories/${category.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(category.id.toString()))
            .andExpect(jsonPath("$.name").value(category.name))
    }

    @Test
    fun `getById should return 404 when not found`() {
        // Given
        val id = UUID.randomUUID()
        every { getCategoryByIdUseCase.execute(GetCategoryByIdQuery(id)) } returns null

        // When & Then
        mockMvc.perform(get("/api/categories/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getAll should return 200 with paged categories`() {
        // Given
        val category1 = Fixtures.sampleCategory()
        val category2 = Fixtures.sampleCategory().copy(name = "Category 2")
        val pagedCategories = Paged(
            items = listOf(category1, category2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )

        every { getAllCategoriesUseCase.execute(GetAllCategoriesQuery(0, 10)) } returns pagedCategories

        // When & Then
        mockMvc.perform(get("/api/categories?page=0&pageSize=10"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.items").isArray)
            .andExpect(jsonPath("$.items.length()").value(2))
            .andExpect(jsonPath("$.totalItems").value(2))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.pageSize").value(10))
    }

    @Test
    fun `getAll should use default pagination when parameters not provided`() {
        // Given
        val pagedCategories = Paged<br.com.manafood.manafoodproduct.domain.model.Category>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )

        every { getAllCategoriesUseCase.execute(GetAllCategoriesQuery(0, 10)) } returns pagedCategories

        // When & Then
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.pageSize").value(10))
    }

    @Test
    fun `create should handle empty category name`() {
        // Given
        val request = CreateCategoryRequest(name = "")
        val category = Fixtures.sampleCategory().copy(name = "")

        every { createCategoryUseCase.execute(any()) } returns category

        // When & Then
        mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
    }
}

