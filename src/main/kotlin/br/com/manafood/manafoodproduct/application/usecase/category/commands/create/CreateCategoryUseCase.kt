package br.com.manafood.manafoodproduct.application.usecase.category.commands.create

import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import java.util.*

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun execute(command: CreateCategoryCommand): Category {
        val category = Category(
            id = UUID.randomUUID(),
            name = command.name,
            createdBy = command.createdBy,
            deleted = false
        )

        try {
            logger.debug("$PREFIX A categoria foi criada com sucesso com name: ${command.name}")
            return categoryRepository.save(category)
        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar criar a categoria: ${command.name}")
            throw Exception("$PREFIX Falha ao tentar criar a categoria", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[CREATE_CATEGORY_USE_CASE]"
    }
}
