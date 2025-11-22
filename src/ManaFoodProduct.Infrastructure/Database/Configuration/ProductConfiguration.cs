using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Infrastructure.Configurations;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class ProductConfiguration : IEntityTypeConfiguration<Product>
{
    public void Configure(EntityTypeBuilder<Product> builder)
    {
        // Aplica configurações base
        builder.ConfigureBaseEntity();

        // Configurações específicas
        builder.ToTable("Products");
        builder.Property(e => e.Name)
            .HasColumnName("name")
            .IsRequired()
            .HasMaxLength(100);
        builder.Property(e => e.Description)
            .HasColumnName("description")
            .IsRequired(false)
            .HasMaxLength(500);
        builder.Property(e => e.CategoryId)
            .HasColumnName("category_id")
            .IsRequired();
        builder.Property(e => e.UnitPrice)
            .HasColumnName("unit_price")
            .IsRequired();
        builder.HasOne(e => e.Category)
            .WithMany()
            .HasForeignKey(e => e.CategoryId)
            .OnDelete(DeleteBehavior.Cascade);

        builder.HasMany(p => p.Items)
            .WithMany()
            .UsingEntity<Dictionary<string, object>>("product_items",
                j => j.HasOne<Item>()
                      .WithMany()
                      .HasForeignKey("item_id")
                      .OnDelete(DeleteBehavior.Cascade),
                j => j.HasOne<Product>()
                      .WithMany()
                      .HasForeignKey("product_id")
                      .OnDelete(DeleteBehavior.Cascade),
                j =>
                {
                    j.HasKey("product_id", "item_id");
                    j.ToTable("product_items");
                    j.HasIndex("product_id");
                    j.HasIndex("item_id");
                });
    }
}