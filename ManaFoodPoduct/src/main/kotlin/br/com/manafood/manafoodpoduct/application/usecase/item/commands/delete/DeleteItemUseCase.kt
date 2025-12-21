package br.com.manafood.manafoodpoduct.application.usecase.item.commands.delete

import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository


class DeleteItemUseCase(
    private val itemRepository: ItemRepository
) {

    fun execute(command: DeleteItemCommand) {

        val item = itemRepository.getBy(command.id)
            ?: throw IllegalArgumentException("Item not found")

        val deleted = item.copy(
            deleted = true,
            updatedBy = command.deletedBy
        )

        itemRepository.create(deleted)
    }
}