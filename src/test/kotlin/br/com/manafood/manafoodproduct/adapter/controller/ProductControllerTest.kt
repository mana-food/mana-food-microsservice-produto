package br.com.manafood.manafoodproduct.adapter.controller

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodproduct.application.usecase.product.commands.create.CreateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.delete.DeleteProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.commands.update.UpdateProductUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getall.GetAllProductsQuery
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getall.GetAllProductsUseCase
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getbyid.GetProductByIdQuery
import br.com.manafood.manafoodproduct.application.usecase.product.queries.getbyid.GetProductByIdUseCase
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
import java.math.BigDecimal
import java.util.*

@WebMvcTest(ProductController::class)
class ProductControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var createProductUseCase: CreateProductUseCase

    @MockkBean
    lateinit var updateProductUseCase: UpdateProductUseCase

    @MockkBean
    lateinit var deleteProductUseCase: DeleteProductUseCase

    @MockkBean
    lateinit var getProductByIdUseCase: GetProductByIdUseCase

    @MockkBean
    lateinit var getAllProductsUseCase: GetAllProductsUseCase

    private val mapper = jacksonObjectMapper()

    @Test
    fun `create should return 200 and body`() {
        val request = CreateProductRequest(
            name = "Produto X",
            description = "Desc",
            unitPrice = BigDecimal("10.0"),
            categoryId = UUID.randomUUID(),
            itemIds = emptyList()
        )

        val product = Fixtures.sampleProduct()
        every { createProductUseCase.execute(any()) } returns product

        mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(product.id.toString()))
    }

    @Test
    fun `create should return 200 with all product details`() {
        // Given
        val request = CreateProductRequest(
            name = "Premium Product",
            description = "High quality product",
            unitPrice = BigDecimal("150.50"),
            categoryId = UUID.randomUUID(),
            itemIds = listOf(UUID.randomUUID())
        )
        val product = Fixtures.sampleProduct()

        every { createProductUseCase.execute(any()) } returns product

        // When & Then
        mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(product.id.toString()))
            .andExpect(jsonPath("$.name").value(product.name))
    }

    @Test
    fun `update should return 200 with updated product`() {
        // Given
        val productId = UUID.randomUUID()
        val request = UpdateProductRequest(
            id = productId,
            name = "Updated Product",
            description = "Updated Description",
            unitPrice = BigDecimal("200.0"),
            categoryId = UUID.randomUUID(),
            itemIds = emptyList()
        )
        val updatedProduct = Fixtures.sampleProduct().copy(id = productId, name = "Updated Product")

        every { updateProductUseCase.execute(any()) } returns updatedProduct

        // When & Then
        mockMvc.perform(
            put("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(productId.toString()))
            .andExpect(jsonPath("$.name").value("Updated Product"))
    }

    @Test
    fun `delete should return 204 when deleted successfully`() {
        // Given
        val id = UUID.randomUUID()

        justRun { deleteProductUseCase.execute(any()) }

        // When & Then
        mockMvc.perform(delete("/api/products/$id"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `getById should return 200 with product when found`() {
        // Given
        val product = Fixtures.sampleProduct()
        every { getProductByIdUseCase.execute(GetProductByIdQuery(product.id)) } returns product

        // When & Then
        mockMvc.perform(get("/api/products/${product.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(product.id.toString()))
            .andExpect(jsonPath("$.name").value(product.name))
    }

    @Test
    fun `getById should return 404 when not found`() {
        val id = UUID.randomUUID()
        every { getProductByIdUseCase.execute(GetProductByIdQuery(id)) } returns null

        mockMvc.perform(get("/api/products/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getAll should return 200 with paged products`() {
        // Given
        val product1 = Fixtures.sampleProduct()
        val product2 = Fixtures.sampleProduct().copy(name = "Product 2")
        val pagedProducts = Paged(
            items = listOf(product1, product2),
            page = 0,
            pageSize = 10,
            totalItems = 2L,
            totalPages = 1
        )

        every { getAllProductsUseCase.execute(GetAllProductsQuery(0, 10)) } returns pagedProducts

        // When & Then
        mockMvc.perform(get("/api/products?page=0&pageSize=10"))
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
        val pagedProducts = Paged<br.com.manafood.manafoodproduct.domain.model.Product>(
            items = emptyList(),
            page = 0,
            pageSize = 10,
            totalItems = 0L,
            totalPages = 0
        )

        every { getAllProductsUseCase.execute(GetAllProductsQuery(0, 10)) } returns pagedProducts

        // When & Then
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.pageSize").value(10))
    }
}
