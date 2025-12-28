package br.com.manafood.manafoodproduct.application.usecase.item.queries.getall

data class GetAllItemsQuery(
    val page: Int = 0,
    val pageSize: Int = 10
)
