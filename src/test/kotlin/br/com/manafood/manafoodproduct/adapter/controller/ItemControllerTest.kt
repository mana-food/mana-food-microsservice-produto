package br.com.manafood.manafoodproduct.adapter.controller

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodproduct.application.usecase.item.commands.create.CreateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.delete.DeleteItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.update.UpdateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsQuery
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdQuery
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdUseCase
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

@WebMvcTest(ItemController::class)
class ItemControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var createItemUseCase: CreateItemUseCase

    @MockkBean
    lateinit var updateItemUseCase: UpdateItemUseCase

    @MockkBean
    lateinit var deleteItemUseCase: DeleteItemUseCase

    @MockkBean
    lateinit var getItemByIdUseCase: GetItemByIdUseCase

    @MockkBean
    lateinit var getAllItemsUseCase: GetAllItemsUseCase

    private val mapper = jacksonObjectMapper()

    @Test
    fun `create should return 200 with item when created successfully`() {
        // Given
        val request = CreateItemRequest(
            name = "Test Item",
            description = "Test Description",
            categoryId = UUID.randomUUID()
        )
        val item = Fixtures.sampleItem()

        every { createItemUseCase.execute(any()) } returns item

        // When & Then
        mockMvc.perform(
            post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(item.id.toString()))
            .andExpect(jsonPath("$.name").value(item.name))
    }

    @Test
    fun `update should return 200 with updated item`() {
        // Given
        val itemId = UUID.randomUUID()
        val request = UpdateItemRequest(
            id = itemId,
            name = "Updated Item",
            description = "Updated Description",
            categoryId = UUID.randomUUID()
        )
        val updatedItem = Fixtures.sampleItem().copy(id = itemId, name = "Updated Item")

        every { updateItemUseCase.execute(any()) } returns updatedItem

        // When & Then
        mockMvc.perform(
            put("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(itemId.toString()))
            .andExpect(jsonPath("$.name").value("Updated Item"))
    }

    @Test
    fun `delete should return 204 when deleted successfully`() {
        // Given
        val id = UUID.randomUUID()

        justRun { deleteItemUseCase.execute(any()) }

        // When & Then
        mockMvc.perform(delete("/api/items/$id"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `getById should return 200 with item when found`() {
        // Given
        val item = Fixtures.sampleItem()
        every { getItemByIdUseCase.execute(GetItemByIdQuery(item.id!!)) } returns item

        // When & Then
        mockMvc.perform(get("/api/items/${item.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(item.id.toString()))
            .andExpect(jsonPath("$.name").value(item.name))
    }

    @Test
    fun `getById should return 404 when not found`() {
        // Given
        val id = UUID.randomUUID()
        every { getItemByIdUseCase.execute(GetItemByIdQuery(id)) } returns null

        // When & Then
        mockMvc.perform(get("/api/items/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getAll should return 200 with paged items`() {
        // Given
        val item1 = Fixtures.sampleItem()
        val item2 = Fixtures.sampleItem().copy(name = "Item 2")
        val pagedItems = Paged(
            items = listOf(item1, item2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )

        every { getAllItemsUseCase.execute(GetAllItemsQuery(0, 10)) } returns pagedItems

        // When & Then
        mockMvc.perform(get("/api/items?page=0&pageSize=10"))
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
        val pagedItems = Paged<br.com.manafood.manafoodproduct.domain.model.Item>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )

        every { getAllItemsUseCase.execute(GetAllItemsQuery(0, 10)) } returns pagedItems

        // When & Then
        mockMvc.perform(get("/api/items"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.pageSize").value(10))
    }
}

