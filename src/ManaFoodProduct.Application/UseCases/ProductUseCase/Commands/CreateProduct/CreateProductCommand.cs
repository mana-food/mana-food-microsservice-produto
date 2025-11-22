using MediatR;
using ManaFoodProduct.Application.Dtos;
using System.Collections.Generic;

namespace ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.CreateProduct
{
    public record CreateProductCommand(string Name, double UnitPrice, Guid CategoryId, List<Guid>? ItemIds = null) : IRequest<ProductDto>;
}

