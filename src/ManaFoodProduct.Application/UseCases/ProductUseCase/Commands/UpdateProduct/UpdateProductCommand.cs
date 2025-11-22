using MediatR;
using ManaFoodProduct.Application.Dtos;
using System.Collections.Generic;

namespace ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.UpdateProduct
{
    public record UpdateProductCommand(Guid Id, string Name, double UnitPrice, Guid CategoryId, List<Guid>? ItemIds = null) : IRequest<ProductDto>;
}

