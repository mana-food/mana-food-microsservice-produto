package br.com.manafood.manafoodproduct.adapter.controller

import br.com.manafood.manafoodproduct.adapter.mapper.CategoryMapper
import br.com.manafood.manafoodproduct.adapter.request.commands.create.CreateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.request.commands.update.UpdateCategoryRequest
import br.com.manafood.manafoodproduct.adapter.response.category.CategoryResponse
import br.com.manafood.manafoodproduct.application.usecase.category.commands.create.CreateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.delete.DeleteCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.commands.update.UpdateCategoryUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesQuery
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getall.GetAllCategoriesUseCase
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdQuery
import br.com.manafood.manafoodproduct.application.usecase.category.queries.getbyid.GetCategoryByIdUseCase
import br.com.manafood.manafoodproduct.domain.common.Paged
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) {

    @PostMapping
    fun create(
        @RequestBody request: CreateCategoryRequest
    ): ResponseEntity<CategoryResponse> {
        val createdBy = UUID.randomUUID()
        val command = CategoryMapper.toCreateCommand(request, createdBy)
        val category = createCategoryUseCase.execute(command)

        return ResponseEntity.ok(CategoryMapper.toResponse(category))
    }

    @PutMapping
    fun update(
        @RequestBody request: UpdateCategoryRequest
    ): ResponseEntity<CategoryResponse> {
        val updatedBy = UUID.randomUUID()
        val command = CategoryMapper.toUpdateCommand(request, updatedBy)
        val category = updateCategoryUseCase.execute(command)

        return ResponseEntity.ok(CategoryMapper.toResponse(category))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        val deletedBy = UUID.randomUUID()
        val command = CategoryMapper.toDeleteCommand(id, deletedBy)
        deleteCategoryUseCase.execute(command)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<CategoryResponse> {
        val category = getCategoryByIdUseCase.execute(GetCategoryByIdQuery(id))
        return if (category != null) ResponseEntity.ok(CategoryMapper.toResponse(category)) else ResponseEntity.notFound()
            .build()
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Paged<CategoryResponse>> {
        val categories = getAllCategoriesUseCase.execute(GetAllCategoriesQuery(page, pageSize))
        return ResponseEntity.ok(CategoryMapper.toResponsePaged(categories))
    }
}
