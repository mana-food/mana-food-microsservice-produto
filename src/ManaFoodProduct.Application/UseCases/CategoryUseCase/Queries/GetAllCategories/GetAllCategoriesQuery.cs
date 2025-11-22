using MediatR;
using ManaFoodProduct.Application.Dtos;
using System.Collections.Generic;

namespace ManaFoodProduct.Application.UseCases.CategoryUseCase.Queries.GetAllCategories
{
    public record GetAllCategoriesQuery() : IRequest<List<CategoryDto>>;
}

