namespace ManaFoodProduct.Application.Dtos;

public record ItemDto : BaseDto
{
    public required string Name { get; init; }
    public string? Description { get; init; }
    public Guid CategoryId { get; init; }
}
