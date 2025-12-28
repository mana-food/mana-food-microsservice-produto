package br.com.manafood.manafoodproduct.application.usecase.product.queries.getall

data class GetAllProductsQuery(
    val page: Int = 0,
    val pageSize: Int = 10
)
