package br.com.manafood.manafoodpoduct.application.usecase.product.queries.getbyid

import br.com.manafood.manafoodpoduct.domain.model.Product
import br.com.manafood.manafoodpoduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class GetProductByIdUseCase(
    private val productRepository: ProductRepository
) {
    fun execute(query: GetProductByIdQuery): Product? {
        try {
            val productFinded = productRepository.findById(query.id)

            if (productFinded != null) {
                logger.debug("{} Produto encontrado - nome: {}", PREFIX, productFinded.name)
            } else {
                logger.debug("{} Produto não encontrado com id: {}",
                    PREFIX, query.id)
            }

            return productFinded

        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar encontrar o produto do id ${query.id}")
            throw Exception("$PREFIX Falha ao tentar encontrar o produto do id ${query.id}", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_BY_ID_PRODUCT_USE_CASE]"
    }
}
