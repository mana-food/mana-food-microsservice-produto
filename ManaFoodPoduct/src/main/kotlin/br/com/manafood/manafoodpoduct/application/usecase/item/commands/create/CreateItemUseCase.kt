package br.com.manafood.manafoodpoduct.application.usecase.item.commands.create

import br.com.manafood.manafoodpoduct.domain.model.Item
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import java.util.*

class CreateItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
) {

    fun execute(command: CreateItemCommand): Item {

        val categoryFinded = categoryRepository.findById(command.categoryId)

        if(categoryFinded == null) {
            logger.warn("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
            throw IllegalArgumentException("$PREFIX Categoria com id ${command.categoryId} não encontrada.")
        }

        val item = Item(
            id = UUID.randomUUID(),
            name = command.name,
            description = command.description,
            category = categoryFinded,
            createdBy = UUID.randomUUID(),
            categoryId = categoryFinded.id!!
        )

        try {
            val itemSaved = itemRepository.save(item)
            logger.debug("$PREFIX O item foi criado com sucesso com name: ${command.name}")
            return itemSaved
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar criar o item: ${command.name}")
            throw Exception("$PREFIX Falha ao tentar criar o item", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[CREATE_ITEM_USE_CASE]"
    }
}
