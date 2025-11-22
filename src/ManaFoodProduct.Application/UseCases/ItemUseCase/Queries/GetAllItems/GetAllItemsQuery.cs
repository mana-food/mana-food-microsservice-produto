using MediatR;
using ManaFoodProduct.Application.Dtos;
using System.Collections.Generic;

namespace ManaFoodProduct.Application.UseCases.ItemUseCase.Queries.GetAllItems
{
    public record GetAllItemsQuery() : IRequest<List<ItemDto>>;
}

