package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.item.commands.create.CreateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.delete.DeleteItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.update.UpdateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ItemUseCaseConfig(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
) {

    @Bean
    fun createItemUseCase(): CreateItemUseCase {
        return CreateItemUseCase(itemRepository, categoryRepository)
    }

    @Bean
    fun updateItemUseCase(): UpdateItemUseCase {
        return UpdateItemUseCase(itemRepository, categoryRepository)
    }

    @Bean
    fun deleteItemUseCase(): DeleteItemUseCase {
        return DeleteItemUseCase(itemRepository)
    }

    @Bean
    fun getItemByIdUseCase(): GetItemByIdUseCase {
        return GetItemByIdUseCase(itemRepository)
    }

    @Bean
    fun getAllItemsUseCase(): GetAllItemsUseCase {
        return GetAllItemsUseCase(itemRepository)
    }
}