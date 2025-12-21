package br.com.manafood.manafoodpoduct.application.usecase.item.commands.update

import br.com.manafood.manafoodpoduct.domain.model.Item
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository

class UpdateItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun execute(command: UpdateItemCommand): Item {

        val item = itemRepository.getBy(command.id)
            ?: throw IllegalArgumentException("Item not found")

        val category = categoryRepository.getBy(command.categoryId)
            ?: throw IllegalArgumentException("Category not found")

        val updated = item.copy(
            name = command.name,
            description = command.description,
            category = category,
            updatedBy = command.updatedBy
        )

        return itemRepository.create(updated)
    }
}