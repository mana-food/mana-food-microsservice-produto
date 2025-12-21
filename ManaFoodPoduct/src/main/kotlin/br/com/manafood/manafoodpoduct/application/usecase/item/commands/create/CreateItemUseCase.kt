package br.com.manafood.manafoodpoduct.application.usecase.item.commands.create

import br.com.manafood.manafoodpoduct.domain.model.Item
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import java.util.*

class CreateItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun execute(command: CreateItemCommand): Item {

        val category = categoryRepository.getBy(command.categoryId)
            ?: throw IllegalArgumentException("Category not found")

        val item = Item(
            id = UUID.randomUUID(),
            name = command.name,
            description = command.description,
            category = category,
            createdBy = UUID.randomUUID(),
            updatedBy = null,
            deleted = TODO(),
            categoryId = TODO()
        )

        return itemRepository.create(item)
    }
}
