using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Application.Interfaces;
using ManaFoodProduct.Infrastructure.Database.Context;
using Microsoft.EntityFrameworkCore;

namespace ManaFoodProduct.Infrastructure.Database.Repositories;

public class ProductRepository : BaseRepository<Product>, IProductRepository
{
    public ProductRepository(ApplicationContext applicationContext) : base(applicationContext) { }
}
