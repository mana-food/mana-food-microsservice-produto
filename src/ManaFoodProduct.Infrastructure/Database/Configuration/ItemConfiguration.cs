using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Infrastructure.Configurations;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class ItemConfiguration : IEntityTypeConfiguration<Item>
{
    public void Configure(EntityTypeBuilder<Item> builder)
    {
        // Aplica configurações base
        builder.ConfigureBaseEntity();

        // Configurações específicas
        builder.ToTable("Items");
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
        builder.HasOne(e => e.Category)
            .WithMany()
            .HasForeignKey(e => e.CategoryId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}