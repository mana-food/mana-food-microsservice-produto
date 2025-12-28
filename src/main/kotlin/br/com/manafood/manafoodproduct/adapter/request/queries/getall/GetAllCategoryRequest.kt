package br.com.manafood.manafoodproduct.adapter.request.queries.getall

data class GetAllCategoryRequest(
    val page: Int = 0,
    val pageSize: Int = 10
)