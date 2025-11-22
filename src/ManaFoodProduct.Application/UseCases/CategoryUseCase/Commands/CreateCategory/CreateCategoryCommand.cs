using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.CreateCategory
{
    public record CreateCategoryCommand(string Name) : IRequest<CategoryDto>;
}

