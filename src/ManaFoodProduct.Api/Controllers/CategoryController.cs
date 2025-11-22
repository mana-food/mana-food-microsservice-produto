using MediatR;
using Microsoft.AspNetCore.Mvc;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.CreateCategory;
using ManaFoodProduct.Application.UseCases.CategoryUseCase.Queries.GetCategoryById;
using ManaFoodProduct.Application.UseCases.CategoryUseCase.Queries.GetAllCategories;
using ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.UpdateCategory;
using ManaFoodProduct.Application.UseCases.CategoryUseCase.Commands.DeleteCategory;
using Microsoft.AspNetCore.Authorization;
using ManaFoodProduct.Application.Constants;

namespace ManaFoodProduct.WebAPI.Controllers
{
    [ApiController]
    [Route("api/categories")]
    public class CategoryController(IMediator mediator) : ControllerBase
    {
        [AllowAnonymous]
        [HttpGet]
        public async Task<ActionResult<CategoryDto>> GetAll([FromQuery] GetAllCategoriesQuery query, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(query, cancellationToken);
            return Ok(result);
        }

        [AllowAnonymous]
        [HttpGet("{id:guid}")]
        public async Task<ActionResult<CategoryDto>> GetById(Guid id, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(new GetCategoryByIdQuery(id), cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPost]
        public async Task<ActionResult<CategoryDto>> Create(CreateCategoryCommand command, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(command, cancellationToken);
            return CreatedAtAction(nameof(GetById), new { id = result.Id }, result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPut("{id:guid}")]
        public async Task<ActionResult<CategoryDto>> Update(Guid id, UpdateCategoryCommand commandBody, CancellationToken cancellationToken)
        {
            var command = new UpdateCategoryWithIdCommand(id, commandBody.Name);
            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpDelete("{id:guid}")]
        public async Task<ActionResult> Delete(Guid id, CancellationToken cancellationToken)
        {
            var command = new DeleteCategoryCommand(id);
            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

    }
}
