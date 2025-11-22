using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.ItemUseCase.Queries.GetItemById
{
    public record GetItemByIdQuery(Guid Id) : IRequest<ItemDto>;
}

