package br.com.manafood.manafoodpoduct.application.usecase.category.commands.delete

import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun execute(command: DeleteCategoryCommand) {
        val categoryFinded = categoryRepository.findById(command.id)
            ?: throw IllegalArgumentException("$PREFIX Categoria não encontrada")

        val deleted = categoryFinded.copy(
            deleted = true,
            updatedBy = command.deletedBy
        )

        logger.debug("{} A categoria foi deletada com sucesso com o nome: {}", PREFIX, categoryFinded.name)

        try {
            logger.debug("$PREFIX A categoria foi deletada com sucesso do name: ${categoryFinded.name}")
            categoryRepository.save(deleted)
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar criar a categoria com nome: ${categoryFinded.name}")
            throw Exception("$PREFIX Falha ao tentar deletar a categoria", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[DELETE_CATEGORY_USE_CASE]"
    }
}
