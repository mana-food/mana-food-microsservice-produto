package br.com.manafood.manafoodproduct.application.usecase.product.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Product
import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory

class GetAllProductsUseCase(
    private val productRepository: ProductRepository
) {
    fun execute(query: GetAllProductsQuery): Paged<Product> {
        val productsListFinded = productRepository.findPaged(
            page = query.page,
            pageSize = query.pageSize
        )

        if(productsListFinded.items.isEmpty()) {
            logger.debug("$PREFIX Foi/Foram encontrado(s) [${productsListFinded.totalItems}] produto(s) na base de dados.")
        } else {
            logger.debug("$PREFIX Não foi encontrado nenhum produto na base de dados.")
        }

        return productsListFinded
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_ALL_PRODUCTS_USE_CASE]"
    }
}
