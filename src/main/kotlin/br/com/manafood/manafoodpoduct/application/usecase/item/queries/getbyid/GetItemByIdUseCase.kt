package br.com.manafood.manafoodpoduct.application.usecase.item.queries.getbyid

import br.com.manafood.manafoodpoduct.domain.model.Item
import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class GetItemByIdUseCase(
    private val itemRepository: ItemRepository
) {
    fun execute(query: GetItemByIdQuery): Item? {
        try {
            val itemFinded = itemRepository.findById(query.id)
            if (itemFinded != null) {
                logger.debug("{} Item encontrado - nome: {}", PREFIX, itemFinded.name)
            } else {
                logger.debug("{} Item não encontrado com id: {}",
                    PREFIX, query.id)
            }

            return itemFinded

        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar encontrar o item do id ${query.id}")
            throw Exception("$PREFIX Falha ao tentar encontrar o item do id ${query.id}", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_BY_ID_ITEM_USE_CASE]"
    }
}
