package br.com.manafood.manafoodpoduct.application.usecase.category.commands.update

import br.com.manafood.manafoodpoduct.domain.model.Category
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class UpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    fun execute(command: UpdateCategoryCommand): Category {
        val categoryFinded = categoryRepository.findById(command.id)
            ?: throw IllegalArgumentException("$PREFIX Categoria não encontrada")

        val updated = categoryFinded.copy(
            name = command.name,
            updatedBy = command.updatedBy
        )

        try {
            logger.debug("$PREFIX A categoria foi atualizada com sucesso, foi atualizado de [${categoryFinded.name}] para [${command.name}]")
            return categoryRepository.save(updated)
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar atualizar a categoria com nome: ${command.name}")
            throw Exception("$PREFIX Erro ao atualizar entidade", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[UPDATE_CATEGORY_USE_CASE]"
    }
}
