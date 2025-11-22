using AutoMapper;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Domain.Entities;

namespace ManaFoodProduct.Application.Mappings;

public sealed class ItemMapper : Profile
{
    public ItemMapper()
    {
        CreateMap<Item, ItemDto>();
    }
}
