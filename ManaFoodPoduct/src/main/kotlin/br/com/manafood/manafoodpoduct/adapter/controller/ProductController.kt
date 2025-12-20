package br.com.manafood.manafoodpoduct.adapter.controller

import br.com.manafood.manafoodpoduct.adapter.mapper.ProductMapper
import br.com.manafood.manafoodpoduct.adapter.request.commands.create.CreateProductRequest
import br.com.manafood.manafoodpoduct.adapter.request.commands.update.UpdateProductRequest
import br.com.manafood.manafoodpoduct.adapter.response.product.ProductResponse
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.create.CreateProductUseCase
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.delete.DeleteProductUseCase
import br.com.manafood.manafoodpoduct.application.usecase.product.commands.update.UpdateProductUseCase
import br.com.manafood.manafoodpoduct.application.usecase.product.queries.getall.GetAllProductsQuery
import br.com.manafood.manafoodpoduct.application.usecase.product.queries.getbyid.GetProductByIdQuery
import br.com.manafood.manafoodpoduct.application.usecase.product.queries.getbyid.GetProductByIdUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase
) {

    @PostMapping
    fun create(
        @RequestBody request: CreateProductRequest
    ): ResponseEntity<ProductResponse> {

        val createdBy = UUID.randomUUID()

        val command = ProductMapper.toCreateCommand(request, createdBy)
        val product = createProductUseCase.execute(command)

        return ResponseEntity.ok(ProductMapper.toResponse(product))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<ProductResponse> {

        val updatedBy = UUID.randomUUID()

        val command = ProductMapper.toUpdateCommand(request, id, updatedBy)
        val product = updateProductUseCase.execute(command)

        return ResponseEntity.ok(ProductMapper.toResponse(product))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        val deletedBy = UUID.randomUUID()

        val command = ProductMapper.toDeleteCommand(id, deletedBy)
        deleteProductUseCase.execute(command)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<ProductResponse> {
        val product = getProductByIdUseCase.execute(GetProductByIdQuery(id))
        return ResponseEntity.ok(ProductMapper.toResponse(product))
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<List<ProductResponse>> {

        val products = getAllProductsUseCase
            .execute(GetAllProductsQuery(page, pageSize))

        return ResponseEntity.ok(products.map(ProductMapper::toResponse))
    }
}
