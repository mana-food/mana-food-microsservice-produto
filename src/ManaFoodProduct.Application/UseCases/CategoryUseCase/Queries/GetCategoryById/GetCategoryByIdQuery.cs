using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.CategoryUseCase.Queries.GetCategoryById
{
    public record GetCategoryByIdQuery(Guid Id) : IRequest<CategoryDto>;
}

