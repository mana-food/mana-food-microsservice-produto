using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Infrastructure.Configurations;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class CategoryConfiguration : IEntityTypeConfiguration<Category>
{
    public void Configure(EntityTypeBuilder<Category> builder)
    {
        // Aplica configurações base
        builder.ConfigureBaseEntity();

        // Configurações específicas
        builder.ToTable("Categories");
        builder.Property(e => e.Name)
            .HasColumnName("name")
            .IsRequired()
            .HasMaxLength(100);
    }
}