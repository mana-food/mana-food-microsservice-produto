using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.ProductUseCase.Queries.GetProductById
{
    public record GetProductByIdQuery(Guid Id) : IRequest<ProductDto>;
}

