package br.com.manafood.manafoodproduct.application.usecase.product.commands.delete

import br.com.manafood.manafoodproduct.domain.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class DeleteProductUseCase(
    private val productRepository: ProductRepository
) {
    fun execute(command: DeleteProductCommand) {
        val productFinded = productRepository.findById(command.id)
        if(productFinded == null) {
            logger.warn("$PREFIX Produto não encontrado para o id [${command.id}].")
            throw IllegalArgumentException("$PREFIX Produto não encontrado para o id [${command.id}].")
        }

        val deleted = productFinded.copy(
            deleted = true,
            updatedBy = command.deletedBy
        )

        try {
            productRepository.save(deleted)
            logger.debug("$PREFIX O produto foi deletado com sucesso: ${productFinded.name}")
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar deletar o produto: ${productFinded.name}")
            throw Exception("$PREFIX Falha ao tentar deletar o produto", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[DELETE_PRODUCT_USE_CASE]"
    }
}