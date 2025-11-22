using MediatR;

namespace ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.DeleteItem
{
    public record DeleteItemCommand(Guid Id) : IRequest<Unit>;
}
