namespace ManaFoodProduct.Domain.Entities;

public class Product : BaseEntity
{
    public required string Name { get; set; }
    public string? Description { get; set; }
    public double UnitPrice { get; set; }
    public Guid CategoryId { get; set; }
    public required Category Category { get; set; }
    public List<Item> Items { get; set; } = new();
}
