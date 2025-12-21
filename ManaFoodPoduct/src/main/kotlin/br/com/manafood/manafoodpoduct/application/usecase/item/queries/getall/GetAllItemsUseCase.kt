package br.com.manafood.manafoodpoduct.application.usecase.item.queries.getall

import br.com.manafood.manafoodpoduct.domain.repository.ItemRepository

class GetAllItemsUseCase(
    private val itemRepository: ItemRepository
) {
    suspend fun execute() = itemRepository.getAll()
}