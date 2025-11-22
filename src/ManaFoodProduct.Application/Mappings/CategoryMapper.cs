using AutoMapper;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Domain.Entities;

namespace ManaFoodProduct.Application.Mappings;

public sealed class CategoryMapper : Profile
{
    public CategoryMapper()
    {
        CreateMap<Category, CategoryDto>();
    }
}
