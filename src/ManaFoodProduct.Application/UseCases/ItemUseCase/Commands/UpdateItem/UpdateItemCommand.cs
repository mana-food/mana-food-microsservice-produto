using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.UpdateItem
{
    public record UpdateItemCommand(Guid Id, string Name, string? Description, Guid CategoryId) : IRequest<ItemDto>;
}

