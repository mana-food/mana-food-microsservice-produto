using MediatR;
using ManaFoodProduct.Application.Dtos;
using System.Collections.Generic;

namespace ManaFoodProduct.Application.UseCases.ProductUseCase.Queries.GetAllProducts
{
    public record GetAllProductsQuery() : IRequest<List<ProductDto>>;
}

