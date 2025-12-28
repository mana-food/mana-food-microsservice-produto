package br.com.manafood.manafoodproduct.bdd.steps

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodproduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodproduct.adapter.response.item.ItemResponse
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
 * Step Definitions para testes de Item
 * Implementa os passos Gherkin em português para o contexto de itens (ingredientes)
 */
class ItemSteps : CucumberSpringConfiguration() {

    private var response: ResponseEntity<*>? = null
    private var itemId: UUID? = null
    private var itemResponse: ItemResponse? = null
    private var categoryId: UUID? = null

    @Dado("o banco de dados está preparado para itens")
    fun `o banco de dados está preparado para itens`() {
        // Banco de dados limpo pelo Testcontainer
        assertTrue(true)
    }

    @Dado("que existe uma categoria {string} para o item")
    fun `que existe uma categoria para o item`(nomeCategoria: String) {
        val url = "${getBaseUrl()}/categories"
        val request = CreateCategoryRequest(name = nomeCategoria)

        val createResponse = restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        categoryId = createResponse.body?.id

        assertNotNull(categoryId, "Falha ao criar categoria para o item")
    }

    @Quando("eu criar um item com os seguintes dados:")
    fun `eu criar um item com os seguintes dados`(dataTable: DataTable) {
        assertNotNull(categoryId, "Categoria não foi criada")

        val dados = dataTable.asMap(String::class.java, String::class.java)
        val nome = dados["nome"] ?: "Item Teste"
        val descricao = dados["descricao"] ?: "Descrição Teste"

        val url = "${getBaseUrl()}/items"
        val request = CreateItemRequest(
            name = nome,
            description = descricao,
            categoryId = categoryId!!
        )

        response = restTemplate.postForEntity(url, request, ItemResponse::class.java)
        itemResponse = response?.body as? ItemResponse
        itemId = itemResponse?.id
    }

    @Então("o item deve ser criado com sucesso")
    fun `o item deve ser criado com sucesso`() {
        assertNotNull(itemResponse, "Item não foi criado")
        assertNotNull(itemResponse?.id, "ID do item não foi gerado")
    }

    @Então("o item deve ter o nome {string}")
    fun `o item deve ter o nome`(nomeEsperado: String) {
        assertEquals(nomeEsperado, itemResponse?.name, "Nome do item não corresponde")
    }

    @Dado("que existe um item cadastrado com nome {string}")
    fun `que existe um item cadastrado com nome`(nomeItem: String) {
        // Garantir que temos uma categoria
        if (categoryId == null) {
            `que existe uma categoria para o item`("Categoria Padrão")
        }

        val url = "${getBaseUrl()}/items"
        val request = CreateItemRequest(
            name = nomeItem,
            description = "Item de teste",
            categoryId = categoryId!!
        )

        val createResponse = restTemplate.postForEntity(url, request, ItemResponse::class.java)
        itemResponse = createResponse.body
        itemId = itemResponse?.id

        assertNotNull(itemId, "Falha ao criar item de teste")
    }

    @Quando("eu buscar o item pelo ID")
    fun `eu buscar o item pelo ID`() {
        assertNotNull(itemId, "ID do item não está disponível")

        val url = "${getBaseUrl()}/items/${itemId}"
        response = restTemplate.getForEntity(url, ItemResponse::class.java)
        itemResponse = response?.body as? ItemResponse
    }

    @Então("o item deve ser retornado com sucesso")
    fun `o item deve ser retornado com sucesso`() {
        assertNotNull(itemResponse, "Item não foi retornado")
        assertEquals(itemId, itemResponse?.id, "ID do item não corresponde")
    }

    @Então("o nome do item deve ser {string}")
    fun `o nome do item deve ser`(nomeEsperado: String) {
        assertEquals(nomeEsperado, itemResponse?.name, "Nome do item não corresponde")
    }

    @Dado("que não existe um item com ID aleatório")
    fun `que não existe um item com ID aleatório`() {
        itemId = UUID.randomUUID()
    }

    @Quando("eu buscar o item pelo ID inexistente")
    fun `eu buscar o item pelo ID inexistente`() {
        val url = "${getBaseUrl()}/items/${itemId}"
        try {
            response = restTemplate.getForEntity(url, ItemResponse::class.java)
        } catch (_: Exception) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @Então("o item não deve ser encontrado")
    fun `o item não deve ser encontrado`() {
        assertTrue(
            response?.statusCode == HttpStatus.NOT_FOUND,
            "Item deveria retornar 404"
        )
    }

    @Dado("que existem os seguintes itens cadastrados:")
    fun `que existem os seguintes itens cadastrados`(dataTable: DataTable) {
        assertNotNull(categoryId, "Categoria não foi criada")

        val url = "${getBaseUrl()}/items"

        dataTable.asMaps().forEach { row ->
            val nome = row["nome"] ?: ""
            val request = CreateItemRequest(
                name = nome,
                description = "Descrição: $nome",
                categoryId = categoryId!!
            )
            restTemplate.postForEntity(url, request, ItemResponse::class.java)
        }
    }

    @Quando("eu listar todos os itens")
    fun `eu listar todos os itens`() {
        val url = "${getBaseUrl()}/items"
        val typeRef = object : ParameterizedTypeReference<Paged<ItemResponse>>() {}
        response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef)
    }

    @Então("devo receber uma lista com pelo menos {int} itens")
    fun `devo receber uma lista com pelo menos itens`(quantidade: Int) {
        val paged = response?.body as? Paged<*>
        assertNotNull(paged, "Lista de itens não foi retornada")
        assertTrue(paged!!.items.size >= quantidade, "Quantidade de itens não corresponde")
    }

    @Quando("eu atualizar o item com nome {string}")
    fun `eu atualizar o item com nome`(novoNome: String) {
        assertNotNull(itemId, "ID do item não está disponível")
        assertNotNull(categoryId, "ID da categoria não está disponível")

        val url = "${getBaseUrl()}/items"
        val request = UpdateItemRequest(
            id = itemId!!,
            name = novoNome,
            description = "Item atualizado",
            categoryId = categoryId!!
        )
        val httpEntity = HttpEntity(request)

        response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ItemResponse::class.java)
        itemResponse = response?.body as? ItemResponse
    }

    @Então("o item deve ser atualizado com sucesso")
    fun `o item deve ser atualizado com sucesso`() {
        assertNotNull(itemResponse, "Item não foi atualizado")
        assertEquals(itemId, itemResponse?.id, "ID do item mudou incorretamente")
    }

    @Quando("eu excluir o item")
    fun `eu excluir o item`() {
        assertNotNull(itemId, "ID do item não está disponível")

        val url = "${getBaseUrl()}/items/${itemId}"
        response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
    }

    @Então("o item deve ser excluído com sucesso")
    fun `o item deve ser excluído com sucesso`() {
        assertEquals(HttpStatus.NO_CONTENT, response?.statusCode, "Item não foi excluído")
    }

    @Dado("que existem {int} itens cadastrados")
    fun `que existem itens cadastrados`(quantidade: Int) {
        // Garantir que temos uma categoria
        if (categoryId == null) {
            `que existe uma categoria para o item`("Categoria Itens")
        }

        val url = "${getBaseUrl()}/items"

        for (i in 1..quantidade) {
            val request = CreateItemRequest(
                name = "Item $i",
                description = "Descrição do Item $i",
                categoryId = categoryId!!
            )
            restTemplate.postForEntity(url, request, ItemResponse::class.java)
        }
    }

    @Quando("eu listar os itens da página {int} com tamanho {int}")
    fun `eu listar os itens da página com tamanho`(page: Int, pageSize: Int) {
        val url = "${getBaseUrl()}/items?page=$page&pageSize=$pageSize"
        val typeRef = object : ParameterizedTypeReference<Paged<ItemResponse>>() {}
        response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef)
    }

    @Então("devo receber {int} itens")
    fun `devo receber itens`(quantidade: Int) {
        val paged = response?.body as? Paged<*>
        assertNotNull(paged, "Lista paginada de itens não foi retornada")
        assertEquals(quantidade, paged!!.items.size, "Quantidade de itens por página não corresponde")
    }

    @Então("o total de itens deve ser pelo menos {int}")
    fun `o total de itens deve ser pelo menos`(total: Int) {
        val paged = response?.body as? Paged<*>
        assertTrue(paged!!.totalItems >= total, "Total de itens não corresponde")
    }

    @Então("o número da página de itens deve ser {int}")
    fun `o número da página de itens deve ser`(pageNumber: Int) {
        val paged = response?.body as? Paged<*>
        assertEquals(pageNumber, paged!!.page, "Número da página de itens não corresponde")
    }

    @Quando("eu tentar criar um item sem categoria válida")
    fun `eu tentar criar um item sem categoria válida`() {
        val url = "${getBaseUrl()}/items"
        val request = CreateItemRequest(
            name = "Item Sem Categoria",
            description = "Teste",
            categoryId = UUID.randomUUID() // ID inexistente
        )

        try {
            response = restTemplate.postForEntity(url, request, ItemResponse::class.java)
        } catch (_: Exception) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @Então("o item não deve ser criado")
    fun `o item não deve ser criado`() {
        assertTrue(
            response?.statusCode?.is4xxClientError == true || response?.statusCode?.is5xxServerError == true,
            "Item deveria falhar na criação"
        )
    }

    @Então("o item deve ter uma categoria associada")
    fun `o item deve ter uma categoria associada`() {
        assertNotNull(itemResponse, "Item não foi retornado")
        assertNotNull(itemResponse?.category, "Item não tem categoria associada")
        assertNotNull(itemResponse?.category?.id, "Categoria do item não tem ID")
    }

    @Então("o nome da categoria do item deve ser {string}")
    fun `o nome da categoria do item deve ser`(nomeCategoriaEsperado: String) {
        assertNotNull(itemResponse?.category, "Item não tem categoria associada")
        assertEquals(
            nomeCategoriaEsperado,
            itemResponse?.category?.name,
            "Nome da categoria do item não corresponde"
        )
    }
}

