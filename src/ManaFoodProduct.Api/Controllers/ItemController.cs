using MediatR;
using Microsoft.AspNetCore.Mvc;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.CreateItem;
using ManaFoodProduct.Application.UseCases.ItemUseCase.Queries.GetItemById;
using ManaFoodProduct.Application.UseCases.ItemUseCase.Queries.GetAllItems;
using ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.UpdateItem;
using ManaFoodProduct.Application.UseCases.ItemUseCase.Commands.DeleteItem;
using Microsoft.AspNetCore.Authorization;
using ManaFoodProduct.Application.Constants;

namespace ManaFoodProduct.WebAPI.Controllers
{
    [ApiController]
    [Route("api/items")]
    public class ItemController(IMediator mediator) : ControllerBase
    {
        [AllowAnonymous]
        [HttpGet]
        public async Task<ActionResult<ItemDto>> GetAll(CancellationToken cancellationToken)
        {
            var result = await mediator.Send(new GetAllItemsQuery(), cancellationToken);
            return Ok(result);
        }

        [AllowAnonymous]
        [HttpGet("{id}")]
        public async Task<ActionResult<ItemDto>> GetById(Guid id, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(new GetItemByIdQuery(id), cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPost]
        public async Task<ActionResult<ItemDto>> Create(CreateItemCommand command, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(command, cancellationToken);
            return CreatedAtAction(nameof(GetById), new { id = result.Id }, result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPut("{id}")]
        public async Task<ActionResult<ItemDto>> Update(Guid id, UpdateItemCommand command, CancellationToken cancellationToken)
        {
            if (id != command.Id)
                return BadRequest("Incompatibilidade de ID entre URL e corpo da solicitação");

            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(Guid id, DeleteItemCommand command, CancellationToken cancellationToken)
        {
            if (id != command.Id)
                return BadRequest("Incompatibilidade de ID entre URL e corpo da solicitação");

            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

    }
}
