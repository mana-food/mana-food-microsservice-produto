using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Application.Interfaces;
using ManaFoodProduct.Infrastructure.Database.Context;

namespace ManaFoodProduct.Infrastructure.Database.Repositories;

public class ItemRepository : BaseRepository<Item>, IItemRepository
{
    public ItemRepository(ApplicationContext applicationContext) : base(applicationContext) { }
}
