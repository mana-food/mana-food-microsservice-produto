using MediatR;
using Microsoft.AspNetCore.Mvc;
using ManaFoodProduct.Application.Dtos;
using ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.CreateProduct;
using ManaFoodProduct.Application.UseCases.ProductUseCase.Queries.GetProductById;
using ManaFoodProduct.Application.UseCases.ProductUseCase.Queries.GetAllProducts;
using ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.UpdateProduct;
using ManaFoodProduct.Application.UseCases.ProductUseCase.Commands.DeleteProduct;
using Microsoft.AspNetCore.Authorization;
using ManaFoodProduct.Application.Constants;

namespace ManaFoodProduct.WebAPI.Controllers
{
    [ApiController]
    [Route("api/products")]
    public class ProductController(IMediator mediator) : ControllerBase
    {
        [AllowAnonymous]
        [HttpGet]
        public async Task<ActionResult<ProductDto>> GetAll(CancellationToken cancellationToken)
        {
            var result = await mediator.Send(new GetAllProductsQuery(), cancellationToken);
            return Ok(result);
        }

        [AllowAnonymous]
        [HttpGet("{id}")]
        public async Task<ActionResult<ProductDto>> GetById(Guid id, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(new GetProductByIdQuery(id), cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPost]
        public async Task<ActionResult<ProductDto>> Create(CreateProductCommand command, CancellationToken cancellationToken)
        {
            var result = await mediator.Send(command, cancellationToken);
            return CreatedAtAction(nameof(GetById), new { id = result.Id }, result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpPut("{id}")]
        public async Task<ActionResult<ProductDto>> Update(Guid id, UpdateProductCommand command, CancellationToken cancellationToken)
        {
            if (id != command.Id)
                return BadRequest("Incompatibilidade de ID entre URL e corpo da solicitação");

            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

        [Authorize(Policy = Policies.ADMIN_OR_MANAGER)]
        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(Guid id, DeleteProductCommand command, CancellationToken cancellationToken)
        {
            if (id != command.Id)
                return BadRequest("Incompatibilidade de ID entre URL e corpo da solicitação");

            var result = await mediator.Send(command, cancellationToken);
            return Ok(result);
        }

    }
}
