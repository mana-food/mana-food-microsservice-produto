package br.com.manafood.manafoodproduct.infrastructure.config

import br.com.manafood.manafoodproduct.application.usecase.item.commands.create.CreateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.delete.DeleteItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.update.UpdateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdUseCase
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import br.com.manafood.manafoodproduct.domain.repository.ItemRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class ItemUseCaseConfigTest {

    private val itemRepository = mockk<ItemRepository>()
    private val categoryRepository = mockk<CategoryRepository>()
    private val config = ItemUseCaseConfig(itemRepository, categoryRepository)

    @Test
    fun `createItemUseCase should return CreateItemUseCase instance`() {
        // When
        val result = config.createItemUseCase()

        // Then
        assertNotNull(result)
        assert(result is CreateItemUseCase)
    }

    @Test
    fun `updateItemUseCase should return UpdateItemUseCase instance`() {
        // When
        val result = config.updateItemUseCase()

        // Then
        assertNotNull(result)
        assert(result is UpdateItemUseCase)
    }

    @Test
    fun `deleteItemUseCase should return DeleteItemUseCase instance`() {
        // When
        val result = config.deleteItemUseCase()

        // Then
        assertNotNull(result)
        assert(result is DeleteItemUseCase)
    }

    @Test
    fun `getItemByIdUseCase should return GetItemByIdUseCase instance`() {
        // When
        val result = config.getItemByIdUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetItemByIdUseCase)
    }

    @Test
    fun `getAllItemsUseCase should return GetAllItemsUseCase instance`() {
        // When
        val result = config.getAllItemsUseCase()

        // Then
        assertNotNull(result)
        assert(result is GetAllItemsUseCase)
    }
}

