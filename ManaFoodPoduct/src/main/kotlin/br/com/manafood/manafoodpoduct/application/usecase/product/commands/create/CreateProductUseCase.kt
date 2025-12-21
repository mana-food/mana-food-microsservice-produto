package br.com.manafood.manafoodpoduct.application.usecase.product.commands.create

import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory
import java.util.UUID

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
) {
    suspend fun execute(request: CreateProductRequest): Product {

        val categoryFinded = categoryRepository.getBy(command)
            ?: throw IllegalArgumentException("$PREFIX Categoria não encontrada")

        val itemsFinded = itemRepository.getByIds(request.itemIds).toMutableList()

        val product = Product(
            name = request.name,
            unitPrice = request.unitPrice,
            description = request.description ?: "",
            categoryId = request.categoryId,
            id = UUID.randomUUID(),
            createdBy = UUID.randomUUID(),
            category = categoryFinded,
            items = itemsFinded
        )

        val productCreated = productRepository.create(product)

        logger.debug("$PREFIX O produto foi criado com sucesso com name: ${productCreated.name}")
        return productCreated
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[CREATE_PRODUCT_USE_CASE]"
    }
}
