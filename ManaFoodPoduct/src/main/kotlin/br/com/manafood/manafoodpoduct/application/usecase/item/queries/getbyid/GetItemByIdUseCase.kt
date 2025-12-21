package br.com.manafood.manafoodpoduct.application.usecase.item.queries.getbyid

import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository

class GetItemByIdUseCase(
    private val itemRepository: ItemRepository
) {

    fun execute(query: GetItemByIdQuery) =
        itemRepository.getById(query.id)
            ?: throw IllegalArgumentException("Item not found")
}