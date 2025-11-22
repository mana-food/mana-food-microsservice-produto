using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Application.Interfaces;
using ManaFoodProduct.Infrastructure.Database.Context;

namespace ManaFoodProduct.Infrastructure.Database.Repositories;

public class CategoryRepository : BaseRepository<Category>, ICategoryRepository
{
    public CategoryRepository(ApplicationContext applicationContext) : base(applicationContext) { }
}
