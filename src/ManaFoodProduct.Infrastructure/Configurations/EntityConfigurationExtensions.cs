using ManaFoodProduct.Domain.Entities;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace ManaFoodProduct.Infrastructure.Configurations;

public static class EntityConfigurationExtensions
{
    public static void ConfigureBaseEntity<T>(this EntityTypeBuilder<T> builder) where T : BaseEntity
    {
        builder.HasKey(e => e.Id);
        builder.Property(e => e.CreatedAt).IsRequired();
        builder.Property(e => e.UpdatedAt).IsRequired();
        builder.Property(e => e.Deleted).IsRequired();
    }
}

