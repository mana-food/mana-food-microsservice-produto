package br.com.manafood.manafoodpoduct.application.usecase.product.commands.update

import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class UpdateProductUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
) {
    fun execute(command: UpdateProductCommand): Product {
        val productFinded = productRepository.findById(command.id)
        if(productFinded == null) {
            logger.warn("$PREFIX Produto com id ${command.id} não encontrado.")
            throw IllegalArgumentException("$PREFIX Produto com id ${command.id} não encontrado.")
        }

        val categoryFinded = categoryRepository.findById(command.categoryId)
        if(categoryFinded == null) {
            logger.warn("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
            throw IllegalArgumentException("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
        }

        var itemsFinded = itemRepository.findByIds(command.itemIds).toMutableList()
        if(itemsFinded.isEmpty()) {
            itemsFinded = productFinded.items
        }

        val product = productFinded.copy(
            name = command.name,
            description = command.description ?: productFinded.description,
            items = itemsFinded,
            unitPrice = command.unitPrice,
            updatedBy = command.updatedBy
        )

        try {
            val productUpdated = productRepository.save(product)
            logger.debug("$PREFIX O produto foi atualizado com sucesso, foi atualizado de [${categoryFinded.name}] para [${command.name}]")
            return productUpdated
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar atualizar o produto: ${command.name}")
            throw Exception("$PREFIX Erro ao atualizar entidade", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[UPDATE_PRODUCT_USE_CASE]"
    }
}
