package br.com.manafood.manafoodpoduct.application.usecase.category.queries.getall

data class GetAllCategoriesQuery(
    val page: Int = 0,
    val pageSize: Int = 10
)
