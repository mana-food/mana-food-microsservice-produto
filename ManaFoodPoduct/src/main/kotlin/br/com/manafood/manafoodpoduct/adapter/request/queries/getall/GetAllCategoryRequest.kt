package br.com.manafood.manafoodpoduct.adapter.request.queries.getall

data class GetAllCategoryRequest(
    val page: Int = 0,
    val pageSize: Int = 10
)