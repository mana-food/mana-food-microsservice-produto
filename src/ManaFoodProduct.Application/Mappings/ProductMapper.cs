using AutoMapper;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Domain.Entities;

namespace ManaFoodProduct.Application.Mappings;

public sealed class ProductMapper : Profile
{
    public ProductMapper()
    {
        CreateMap<Product, ProductDto>();
    }
}
