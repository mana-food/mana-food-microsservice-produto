package br.com.manafood.manafoodproduct.application.usecase.item.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Item
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import org.slf4j.LoggerFactory

class GetAllItemsUseCase(
    private val itemRepository: ItemRepository
) {
    fun execute(query: GetAllItemsQuery): Paged<Item> {
        val itemsListFinded = itemRepository.findPaged(
            page = query.page,
            pageSize = query.pageSize
        )

        if(itemsListFinded.items.isNotEmpty()) {
            logger.debug("$PREFIX Foi/Foram encontrado(s) [${itemsListFinded.totalItems}] item(s) na base de dados.")
        } else {
            logger.debug("$PREFIX Não foi encontrado nenhum item na base de dados.")
        }

        return itemsListFinded
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_ALL_ITEMS_USE_CASE]"
    }
}