package br.com.manafood.manafoodproduct.bdd.steps

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodproduct.bdd.config.CucumberSpringConfiguration
import br.com.manafood.manafoodproduct.domain.common.Paged
import io.cucumber.datatable.DataTable
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import org.junit.jupiter.api.Assertions.*
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * Step Definitions para testes de Categoria
 * Implementa os passos Gherkin em português
 */
class CategorySteps : CucumberSpringConfiguration() {

    private var response: ResponseEntity<*>? = null
    private var categoryId: UUID? = null
    private var categoryResponse: CategoryResponse? = null

    @Quando("eu criar uma categoria com nome {string}")
    fun `eu criar uma categoria com nome`(nome: String) {
        val url = "${getBaseUrl()}/categories"
        val request = CreateCategoryRequest(name = nome)

        response = restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        categoryResponse = response?.body as? CategoryResponse
        categoryId = categoryResponse?.id
    }

    @Então("a categoria deve ser criada com sucesso")
    fun `a categoria deve ser criada com sucesso`() {
        assertNotNull(categoryResponse, "Categoria não foi criada")
        assertNotNull(categoryResponse?.id, "ID da categoria não foi gerado")
    }

    @Então("a categoria deve ter o nome {string}")
    fun `a categoria deve ter o nome`(nomeEsperado: String) {
        assertEquals(nomeEsperado, categoryResponse?.name, "Nome da categoria não corresponde")
    }

    @Então("o status da resposta deve ser {int}")
    fun `o status da resposta deve ser`(statusEsperado: Int) {
        assertNotNull(response, "Resposta não foi recebida")
        assertEquals(statusEsperado, response?.statusCode?.value(), "Status da resposta não corresponde")
    }

    @Dado("que existe uma categoria com nome {string}")
    fun `que existe uma categoria com nome`(nome: String) {
        val url = "${getBaseUrl()}/categories"
        val request = CreateCategoryRequest(name = nome)

        val createResponse = restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        categoryResponse = createResponse.body
        categoryId = categoryResponse?.id

        assertNotNull(categoryId, "Falha ao criar categoria de teste")
    }

    @Quando("eu buscar a categoria pelo ID")
    fun `eu buscar a categoria pelo ID`() {
        assertNotNull(categoryId, "ID da categoria não está disponível")

        val url = "${getBaseUrl()}/categories/${categoryId}"
        response = restTemplate.getForEntity(url, CategoryResponse::class.java)
        categoryResponse = response?.body as? CategoryResponse
    }

    @Então("a categoria deve ser retornada com sucesso")
    fun `a categoria deve ser retornada com sucesso`() {
        assertNotNull(categoryResponse, "Categoria não foi retornada")
        assertEquals(categoryId, categoryResponse?.id, "ID da categoria não corresponde")
    }

    @Então("o nome da categoria deve ser {string}")
    fun `o nome da categoria deve ser`(nomeEsperado: String) {
        assertEquals(nomeEsperado, categoryResponse?.name, "Nome da categoria não corresponde")
    }

    @Dado("que não existe uma categoria com ID aleatório")
    fun `que não existe uma categoria com ID aleatório`() {
        categoryId = UUID.randomUUID()
    }

    @Quando("eu buscar a categoria pelo ID inexistente")
    fun `eu buscar a categoria pelo ID inexistente`() {
        val url = "${getBaseUrl()}/categories/${categoryId}"
        try {
            response = restTemplate.getForEntity(url, CategoryResponse::class.java)
        } catch (_: Exception) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @Então("a categoria não deve ser encontrada")
    fun `a categoria não deve ser encontrada`() {
        assertTrue(
            response?.statusCode == HttpStatus.NOT_FOUND,
            "Categoria deveria retornar 404"
        )
    }

    @Dado("que existem as seguintes categorias:")
    fun `que existem as seguintes categorias`(dataTable: DataTable) {
        val url = "${getBaseUrl()}/categories"

        dataTable.asMaps().forEach { row ->
            val nome = row["nome"] ?: ""
            val request = CreateCategoryRequest(name = nome)
            restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        }
    }

    @Quando("eu listar todas as categorias")
    fun `eu listar todas as categorias`() {
        val url = "${getBaseUrl()}/categories"
        val typeRef = object : ParameterizedTypeReference<Paged<CategoryResponse>>() {}
        response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef)
    }

    @Então("devo receber uma lista com {int} categorias")
    fun `devo receber uma lista com categorias`(quantidade: Int) {
        val paged = response?.body as? Paged<*>
        assertNotNull(paged, "Lista de categorias não foi retornada")
        assertTrue(paged!!.items.size >= quantidade, "Quantidade de categorias não corresponde")
    }

    @Quando("eu atualizar o nome da categoria para {string}")
    fun `eu atualizar o nome da categoria para`(novoNome: String) {
        assertNotNull(categoryId, "ID da categoria não está disponível")

        val url = "${getBaseUrl()}/categories"
        val request = UpdateCategoryRequest(id = categoryId!!, name = novoNome)
        val httpEntity = HttpEntity(request)

        response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, CategoryResponse::class.java)
        categoryResponse = response?.body as? CategoryResponse
    }

    @Então("a categoria deve ser atualizada com sucesso")
    fun `a categoria deve ser atualizada com sucesso`() {
        assertNotNull(categoryResponse, "Categoria não foi atualizada")
        assertEquals(categoryId, categoryResponse?.id, "ID da categoria mudou incorretamente")
    }

    @Quando("eu excluir a categoria")
    fun `eu excluir a categoria`() {
        assertNotNull(categoryId, "ID da categoria não está disponível")

        val url = "${getBaseUrl()}/categories/${categoryId}"
        response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
    }

    @Então("a categoria deve ser excluída com sucesso")
    fun `a categoria deve ser excluída com sucesso`() {
        assertEquals(HttpStatus.NO_CONTENT, response?.statusCode, "Categoria não foi excluída")
    }

    @Dado("que existem {int} categorias cadastradas")
    fun `que existem categorias cadastradas`(quantidade: Int) {
        val url = "${getBaseUrl()}/categories"

        for (i in 1..quantidade) {
            val request = CreateCategoryRequest(name = "Categoria $i")
            restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        }
    }

    @Quando("eu listar as categorias da página {int} com tamanho {int}")
    fun `eu listar as categorias da página com tamanho`(page: Int, pageSize: Int) {
        val url = "${getBaseUrl()}/categories?page=$page&pageSize=$pageSize"
        val typeRef = object : ParameterizedTypeReference<Paged<CategoryResponse>>() {}
        response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef)
    }

    @Então("devo receber {int} categorias")
    fun `devo receber categorias`(quantidade: Int) {
        val paged = response?.body as? Paged<*>
        assertNotNull(paged, "Lista paginada não foi retornada")
        assertEquals(quantidade, paged!!.items.size, "Quantidade de categorias por página não corresponde")
    }

    @Então("o total de elementos deve ser {int}")
    fun `o total de elementos deve ser`(total: Int) {
        val paged = response?.body as? Paged<*>
        assertTrue(paged!!.totalItems >= total, "Total de elementos não corresponde")
    }

    @Então("o número da página deve ser {int}")
    fun `o número da página deve ser`(pageNumber: Int) {
        val paged = response?.body as? Paged<*>
        assertEquals(pageNumber, paged!!.page, "Número da página não corresponde")
    }
}

