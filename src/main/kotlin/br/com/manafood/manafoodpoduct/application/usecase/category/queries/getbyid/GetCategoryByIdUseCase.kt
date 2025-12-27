package br.com.manafood.manafoodpoduct.application.usecase.category.queries.getbyid

import br.com.manafood.manafoodpoduct.domain.model.Category
import br.com.manafood.manafoodpoduct.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException

class GetCategoryByIdUseCase(
    private val categoryRepository: CategoryRepository
) {

    fun execute(query: GetCategoryByIdQuery): Category? {
        try {
            val categoryFinded = categoryRepository.findById(query.id)
            if (categoryFinded != null) {
                logger.debug(
                    "{} Categoria encontrada - nome: {}",
                    PREFIX, categoryFinded.name
                )
            } else {
                logger.debug("{} Categoria não encontrada com id: {}", PREFIX, query.id)
            }

            return categoryFinded

        } catch (ex: DataAccessException) {
            logger.error("$PREFIX Falha ao tentar encontrar a categoria do id ${query.id}")
            throw Exception("$PREFIX Falha ao tentar encontrar a categoria do id ${query.id}", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_BY_ID_CATEGORY_USE_CASE]"
    }
}
