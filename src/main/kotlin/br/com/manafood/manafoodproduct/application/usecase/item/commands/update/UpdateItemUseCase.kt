package br.com.manafood.manafoodproduct.application.usecase.item.commands.update

import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class UpdateItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
) {
    fun execute(command: UpdateItemCommand): Item {
        val itemFinded = itemRepository.findById(command.id)
        if(itemFinded == null) {
            logger.warn("$PREFIX Item com id ${command.categoryId} não encontrado.")
            throw IllegalArgumentException("$PREFIX Item com id ${command.categoryId} não encontrado.")
        }

        val categoryFinded = categoryRepository.findById(command.categoryId)
        if(categoryFinded == null) {
            logger.warn("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
            throw IllegalArgumentException("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
        }

        val updated = itemFinded.copy(
            name = command.name,
            description = command.description,
            category = categoryFinded,
            updatedBy = command.updatedBy
        )
        
        try {
            val itemUpdated = itemRepository.save(updated)
            logger.debug("$PREFIX O item foi atualizado com sucesso, foi atualizado de [${categoryFinded.name}] para [${command.name}]")
            return itemUpdated
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar atualizar a categoria: ${command.name}")
            throw Exception("$PREFIX Erro ao atualizar entidade", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[UPDATE_ITEM_USE_CASE]"
    }
}