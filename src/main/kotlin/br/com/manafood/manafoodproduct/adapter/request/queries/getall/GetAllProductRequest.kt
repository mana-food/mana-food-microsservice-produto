package br.com.manafood.manafoodproduct.adapter.request.queries.getall

data class GetAllProductRequest(
    val page: Int = 0,
    val pageSize: Int = 10
)
