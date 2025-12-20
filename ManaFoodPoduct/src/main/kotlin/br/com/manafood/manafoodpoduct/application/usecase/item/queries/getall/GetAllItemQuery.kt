package br.com.manafood.manafoodpoduct.application.usecase.item.queries.getall

data class GetAllItemsQuery(
    val page: Int = 0,
    val pageSize: Int = 10
)
