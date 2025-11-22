namespace ManaFoodProduct.Application.Dtos;

using System.Collections.Generic;

public record ProductDto : BaseDto
{
    public required string Name { get; init; }
    public string? Description { get; init; }
    public Guid CategoryId { get; init; }
    public required double UnitPrice { get; init; }
    public required List<Guid> ItemIds { get; set; } = new List<Guid>();
}
