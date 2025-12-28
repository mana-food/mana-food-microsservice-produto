package br.com.manafood.manafoodproduct.adapter.controller

import br.com.manafood.manafoodproduct.adapter.mapper.ItemMapper
import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateItemRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateItemRequest
import br.com.manafood.manafoodproduct.adapter.response.item.ItemResponse
import br.com.manafood.manafoodproduct.application.usecase.item.commands.create.CreateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.delete.DeleteItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.commands.update.UpdateItemUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsQuery
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getall.GetAllItemsUseCase
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdQuery
import br.com.manafood.manafoodproduct.application.usecase.item.queries.getbyid.GetItemByIdUseCase
import br.com.manafood.manafoodproduct.domain.common.Paged
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/items")
class ItemController(
    private val createItemUseCase: CreateItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val getAllItemsUseCase: GetAllItemsUseCase
) {

    @PostMapping
    fun create(
        @RequestBody request: CreateItemRequest
    ): ResponseEntity<ItemResponse> {
        val createdBy = UUID.randomUUID()
        val command = ItemMapper.toCreateCommand(request, createdBy)
        val item = createItemUseCase.execute(command)

        return ResponseEntity.ok(ItemMapper.toResponse(item))
    }

    @PutMapping
    fun update(
        @RequestBody request: UpdateItemRequest
    ): ResponseEntity<ItemResponse> {
        val updatedBy = UUID.randomUUID()
        val command = ItemMapper.toUpdateCommand(request, updatedBy)
        val item = updateItemUseCase.execute(command)

        return ResponseEntity.ok(ItemMapper.toResponse(item))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        val deletedBy = UUID.randomUUID()
        val command = ItemMapper.toDeleteCommand(id, deletedBy)
        deleteItemUseCase.execute(command)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<ItemResponse> {
        val item = getItemByIdUseCase.execute(GetItemByIdQuery(id))
        return if (item != null) ResponseEntity.ok(ItemMapper.toResponse(item)) else ResponseEntity.notFound()
            .build()
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Paged<ItemResponse>> {
        val items = getAllItemsUseCase.execute(GetAllItemsQuery(page, pageSize))
        return ResponseEntity.ok(ItemMapper.toResponsePaged(items))
    }
}
