package br.com.manafood.manafoodpoduct.application.usecase.product.commands.create

import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import java.util.UUID

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
) {
    fun execute(command: CreateProductCommand): Product {

        val categoryFinded = categoryRepository.findById(command.categoryId)
        if(categoryFinded == null) {
            logger.warn("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
            throw IllegalArgumentException("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
        }

        val itemsFinded = itemRepository.findByIds(command.itemIds).toMutableList()
        if(itemsFinded.isEmpty()) {
            logger.warn("$PREFIX Item com id ${command.categoryId} não encontrada.")
            throw IllegalArgumentException("$PREFIX Item com id ${command.categoryId} não encontrada.")
        }
        
        val product = Product(
            name = command.name,
            unitPrice = command.unitPrice,
            description = command.description ?: "",
            categoryId = command.categoryId,
            id = UUID.randomUUID(),
            createdBy = UUID.randomUUID(),
            category = categoryFinded,
            items = itemsFinded
        )

        try {
            val productSaved = productRepository.save(product)
            logger.debug("$PREFIX O produto foi criado com sucesso com name: ${command.name}")
            return productSaved
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar criar o produto: ${command.name}")
            throw Exception("$PREFIX Falha ao tentar criar o produto", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[CREATE_PRODUCT_USE_CASE]"
    }
}
