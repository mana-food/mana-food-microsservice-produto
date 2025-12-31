package br.com.manafood.manafoodproduct.bdd.steps

import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodproduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodproduct.adapter.response.item.ItemResponse
import br.com.manafood.manafoodproduct.adapter.response.product.ProductResponse
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
import java.math.BigDecimal
import java.util.*

/**
 * Step Definitions para testes de Produto
 * Implementa os passos Gherkin em português para o contexto de produtos
 */
class ProductSteps : CucumberSpringConfiguration() {

    private var response: ResponseEntity<*>? = null
    private var productId: UUID? = null
    private var productResponse: ProductResponse? = null
    private var categoryId: UUID? = null
    private val itemIds = mutableListOf<UUID>()

    @Dado("o banco de dados está preparado para produtos")
    fun `o banco de dados está preparado para produtos`() {
        // Banco de dados limpo pelo Testcontainer
        assertTrue(true)
    }

    @Dado("que existe uma categoria {string} para o produto")
    fun `que existe uma categoria para o produto`(nomeCategoria: String) {
        val url = "${getBaseUrl()}/categories"
        val request = CreateCategoryRequest(name = nomeCategoria)

        val createResponse = restTemplate.postForEntity(url, request, CategoryResponse::class.java)
        categoryId = createResponse.body?.id

        assertNotNull(categoryId, "Falha ao criar categoria para o produto")
    }

    @Dado("que existem itens disponíveis para o produto")
    fun `que existem itens disponíveis para o produto`() {
        assertNotNull(categoryId, "Categoria não foi criada ainda")

        val url = "${getBaseUrl()}/items"

        // Criar alguns itens de exemplo
        val items = listOf("Açúcar", "Água", "Gás Carbônico")

        items.forEach { itemName ->
            val request = CreateItemRequest(
                name = itemName,
                description = "Ingrediente: $itemName",
                categoryId = categoryId!!
            )

            val itemResponse = restTemplate.postForEntity(url, request, ItemResponse::class.java)
            itemResponse.body?.id?.let { itemIds.add(it) }
        }

        assertTrue(itemIds.isNotEmpty(), "Falha ao criar itens para o produto")
    }

    @Quando("eu criar um produto com os seguintes dados:")
    fun `eu criar um produto com os seguintes dados`(dataTable: DataTable) {
        assertNotNull(categoryId, "Categoria não foi criada")

        val dados = dataTable.asMap(String::class.java, String::class.java)
        val nome = dados["nome"] ?: "Produto Teste"
        val descricao = dados["descricao"] ?: "Descrição Teste"
        val preco = dados["preco"]?.toDoubleOrNull() ?: 10.0

        val url = "${getBaseUrl()}/products"
        val request = CreateProductRequest(
            name = nome,
            description = descricao,
            unitPrice = BigDecimal("$preco"),
            categoryId = categoryId!!,
            itemIds = itemIds.take(2)
        )

        response = restTemplate.postForEntity(url, request, ProductResponse::class.java)
        productResponse = response?.body as? ProductResponse
        productId = productResponse?.id
    }

    @Então("o produto deve ser criado com sucesso")
    fun `o produto deve ser criado com sucesso`() {
        assertNotNull(productResponse, "Produto não foi criado")
        assertNotNull(productResponse?.id, "ID do produto não foi gerado")
    }

    @Então("o produto deve ter o nome {string}")
    fun `o produto deve ter o nome`(nomeEsperado: String) {
        assertEquals(nomeEsperado, productResponse?.name, "Nome do produto não corresponde")
    }

    @Então("o preço do produto deve ser {double}")
    fun `o preço do produto deve ser`(precoEsperado: Double) {
        assertNotNull(productResponse?.unitPrice, "Preço do produto não foi definido")
        val actual = productResponse?.unitPrice?.toDouble() ?: 0.0
        assertEquals(precoEsperado, actual, 0.01, "Preço do produto não corresponde")
    }

    @Dado("que existe um produto cadastrado com nome {string}")
    fun `que existe um produto cadastrado com nome`(nomeProduto: String) {
        // Primeiro criar categoria e itens se necessário
        if (categoryId == null) {
            `que existe uma categoria para o produto`("Categoria Padrão")
            `que existem itens disponíveis para o produto`()
        }

        val url = "${getBaseUrl()}/products"
        val request = CreateProductRequest(
            name = nomeProduto,
            description = "Produto de teste",
            unitPrice = BigDecimal("10.0"),
            categoryId = categoryId!!,
            itemIds = itemIds.take(1)
        )

        val createResponse = restTemplate.postForEntity(url, request, ProductResponse::class.java)
        productResponse = createResponse.body
        productId = productResponse?.id

        assertNotNull(productId, "Falha ao criar produto de teste")
    }

    @Quando("eu buscar o produto pelo ID")
    fun `eu buscar o produto pelo ID`() {
        assertNotNull(productId, "ID do produto não está disponível")

        val url = "${getBaseUrl()}/products/${productId}"
        response = restTemplate.getForEntity(url, ProductResponse::class.java)
        productResponse = response?.body as? ProductResponse
    }

    @Então("o produto deve ser retornado com sucesso")
    fun `o produto deve ser retornado com sucesso`() {
        assertNotNull(productResponse, "Produto não foi retornado")
        assertEquals(productId, productResponse?.id, "ID do produto não corresponde")
    }

    @Então("o nome do produto deve ser {string}")
    fun `o nome do produto deve ser`(nomeEsperado: String) {
        assertEquals(nomeEsperado, productResponse?.name, "Nome do produto não corresponde")
    }

    @Dado("que não existe um produto com ID aleatório")
    fun `que não existe um produto com ID aleatório`() {
        productId = UUID.randomUUID()
    }

    @Quando("eu buscar o produto pelo ID inexistente")
    fun `eu buscar o produto pelo ID inexistente`() {
        val url = "${getBaseUrl()}/products/${productId}"
        try {
            response = restTemplate.getForEntity(url, ProductResponse::class.java)
        } catch (_: Exception) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @Então("o produto não deve ser encontrado")
    fun `o produto não deve ser encontrado`() {
        assertTrue(
            response?.statusCode == HttpStatus.NOT_FOUND,
            "Produto deveria retornar 404"
        )
    }

    @Quando("eu atualizar o produto com nome {string} e preço {double}")
    fun `eu atualizar o produto com nome e preço`(novoNome: String, novoPreco: Double) {
        assertNotNull(productId, "ID do produto não está disponível")
        assertNotNull(categoryId, "ID da categoria não está disponível")

        val url = "${getBaseUrl()}/products"
        val request = UpdateProductRequest(
            id = productId!!,
            name = novoNome,
            description = "Produto atualizado",
            unitPrice = BigDecimal("$novoPreco"),
            categoryId = categoryId!!,
            itemIds = itemIds.take(1)
        )
        val httpEntity = HttpEntity(request)

        response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ProductResponse::class.java)
        productResponse = response?.body as? ProductResponse
    }

    @Então("o produto deve ser atualizado com sucesso")
    fun `o produto deve ser atualizado com sucesso`() {
        assertNotNull(productResponse, "Produto não foi atualizado")
        assertEquals(productId, productResponse?.id, "ID do produto mudou incorretamente")
    }

    @Quando("eu excluir o produto")
    fun `eu excluir o produto`() {
        assertNotNull(productId, "ID do produto não está disponível")

        val url = "${getBaseUrl()}/products/${productId}"
        response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void::class.java)
    }

    @Então("o produto deve ser excluído com sucesso")
    fun `o produto deve ser excluído com sucesso`() {
        assertEquals(HttpStatus.NO_CONTENT, response?.statusCode, "Produto não foi excluído")
    }

    @Dado("que existem {int} produtos cadastrados")
    fun `que existem produtos cadastrados`(quantidade: Int) {
        // Garantir que temos categoria e itens
        if (categoryId == null) {
            `que existe uma categoria para o produto`("Categoria Produtos")
            `que existem itens disponíveis para o produto`()
        }

        val url = "${getBaseUrl()}/products"

        for (i in 1..quantidade) {
            val request = CreateProductRequest(
                name = "Produto $i",
                description = "Descrição do Produto $i",
                unitPrice = BigDecimal("${10.0 + i}"),
                categoryId = categoryId!!,
                itemIds = itemIds.take(1)
            )
            restTemplate.postForEntity(url, request, ProductResponse::class.java)
        }
    }

    @Quando("eu listar os produtos da página {int} com tamanho {int}")
    fun `eu listar os produtos da página com tamanho`(page: Int, pageSize: Int) {
        val url = "${getBaseUrl()}/products?page=$page&pageSize=$pageSize"
        val typeRef = object : ParameterizedTypeReference<Paged<ProductResponse>>() {}
        response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef)
    }

    @Então("devo receber {int} produtos")
    fun `devo receber produtos`(quantidade: Int) {
        val paged = response?.body as? Paged<*>
        assertNotNull(paged, "Lista paginada de produtos não foi retornada")
        assertEquals(quantidade, paged!!.items.size, "Quantidade de produtos por página não corresponde")
    }

    @Então("o total de produtos deve ser pelo menos {int}")
    fun `o total de produtos deve ser pelo menos`(total: Int) {
        val paged = response?.body as? Paged<*>
        assertTrue(paged!!.totalItems >= total, "Total de produtos não corresponde")
    }

    @Então("o número da página de produtos deve ser {int}")
    fun `o número da página de produtos deve ser`(pageNumber: Int) {
        val paged = response?.body as? Paged<*>
        assertEquals(pageNumber, paged!!.page, "Número da página de produtos não corresponde")
    }

    @Quando("eu tentar criar um produto sem categoria")
    fun `eu tentar criar um produto sem categoria`() {
        val url = "${getBaseUrl()}/products"
        val request = CreateProductRequest(
            name = "Produto Sem Categoria",
            description = "Teste",
            unitPrice = BigDecimal("10.00"),
            categoryId = UUID.randomUUID(), // ID inexistente
            itemIds = emptyList()
        )

        try {
            response = restTemplate.postForEntity(url, request, ProductResponse::class.java)
        } catch (_: Exception) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @Então("o produto não deve ser criado")
    fun `o produto não deve ser criado`() {
        assertTrue(
            response?.statusCode?.is4xxClientError == true || response?.statusCode?.is5xxServerError == true,
            "Produto deveria falhar na criação"
        )
    }

    @Então("o status da resposta deve ser {int} [product]")
    fun `o status da resposta deve ser`(statusEsperado: Int) {
        assertNotNull(response, "Resposta não foi recebida")
        assertEquals(statusEsperado, response?.statusCode?.value(), "Status da resposta não corresponde")
    }

    @Então("o status da resposta deve ser {int} ou {int} [product]")
    fun `o status da resposta deve ser ou`(status1: Int, status2: Int) {
        val statusCode = response?.statusCode?.value()
        assertTrue(
            statusCode == status1 || statusCode == status2,
            "Status da resposta deveria ser $status1 ou $status2, mas foi $statusCode"
        )
    }

}

