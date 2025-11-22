namespace ManaFoodProduct.Domain.Entities;

public class Item : BaseEntity
{
    public required string Name { get; set; }
    public string? Description { get; set; }
    public Guid CategoryId { get; set; }
    public required Category Category { get; set; }
}
