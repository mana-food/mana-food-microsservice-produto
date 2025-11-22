using MediatR;

namespace ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.DeleteProduct
{
    public record DeleteProductCommand(Guid Id) : IRequest<Unit>;
}
