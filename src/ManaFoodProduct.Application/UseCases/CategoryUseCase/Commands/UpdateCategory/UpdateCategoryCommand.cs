using MediatR;
using ManaFoodProduct.Application.Dtos;

namespace ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.UpdateCategory
{
    public record UpdateCategoryCommand(Guid Id, string Name) : IRequest<CategoryDto>;
    public record UpdateCategoryWithIdCommand(Guid Id, string Name) : IRequest<CategoryDto>;
}

