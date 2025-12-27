package br.com.manafood.manafoodpoduct.application.usecase.item.commands.delete

import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class DeleteItemUseCase(
    private val itemRepository: ItemRepository
) {
    fun execute(command: DeleteItemCommand) {
        val itemFinded = itemRepository.findById(command.id)
        if(itemFinded == null) {
            logger.warn("$PREFIX Item com id ${command.id} não encontrado.")
            throw IllegalArgumentException("$PREFIX Item com id ${command.id} não encontrado.")
        }

        val deleted = itemFinded.copy(
            deleted = true,
            updatedBy = command.deletedBy
        )

        try {
            itemRepository.save(deleted)
            logger.debug("$PREFIX O item foi deletado com sucesso: ${itemFinded.name}")
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar deletar o item: ${itemFinded.name}")
            throw Exception("$PREFIX Falha ao tentar deletar o item", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[DELETE_ITEM_USE_CASE]"
    }
}
