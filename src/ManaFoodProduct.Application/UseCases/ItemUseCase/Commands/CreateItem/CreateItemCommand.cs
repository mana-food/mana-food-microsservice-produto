using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.CreateItem
{
    public record CreateItemCommand(string Name, string? Description, Guid CategoryId) : IRequest<ItemDto>;
}

