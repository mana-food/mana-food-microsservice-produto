package br.com.manafood.manafoodproduct.application.usecase.category.queries.getall

import br.com.manafood.manafoodproduct.domain.common.Paged
import br.com.manafood.manafoodproduct.domain.model.Category
import br.com.manafood.manafoodproduct.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory

class GetAllCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {

    fun execute(query: GetAllCategoriesQuery): Paged<Category> {
        val categoriesListFinded = categoryRepository.findPaged(
            page = query.page,
            pageSize = query.pageSize
        )

        if(categoriesListFinded.items.isEmpty()) {
           logger.debug("$PREFIX Foi/Foram encontrado(s) [${categoriesListFinded.totalItems}] categoria(s) na base de dados.")
        } else {
            logger.debug("$PREFIX Não foi encontrado nenhuma categoria na base de dados.")
        }

        return categoriesListFinded
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val PREFIX = "[GET_ALL_CATEGORY_USE_CASE]"
    }
}
