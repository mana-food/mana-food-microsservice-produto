package br.com.manafood.manafoodproduct.application.usecase.category.commands.delete

import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun execute(command: DeleteCategoryCommand) {
        val categoryFinded = categoryRepository.findById(command.id)
        if(categoryFinded == null) {
            logger.warn("$PREFIX Categoria com id ${command.id} não encontrada.")
            throw IllegalArgumentException("$PREFIX Categoria com id ${command.id} não encontrada.")
        }

        val deleted = categoryFinded.copy(
            deleted = true,
            updatedBy = command.deletedBy
        )

        try {
            categoryRepository.save(deleted)
            logger.debug("$PREFIX A categoria foi deletada com sucesso: ${categoryFinded.name}")
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar deletar a categoria: ${categoryFinded.name}")
            throw Exception("$PREFIX Falha ao tentar deletar a categoria", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[DELETE_CATEGORY_USE_CASE]"
    }
}
