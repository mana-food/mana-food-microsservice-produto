using MediatR;

namespace ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.DeleteCategory
{
    public record DeleteCategoryCommand(Guid Id) : IRequest<Unit>;
}
