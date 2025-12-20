package br.com.manafood.manafoodpoduct.application.usecase.product.queries.getall

data class GetAllProductsQuery(
    val page: Int = 0,
    val pageSize: Int = 10
)
