using System.Linq.Expressions;
using ManaFoodProduct.Application.Shared;
using ManaFoodProduct.Domain.Entities;
using ManaFoodProduct.Application.Interfaces;
using ManaFoodProduct.Infrastructure.Database.Context;
using Microsoft.EntityFrameworkCore;

namespace ManaFoodProduct.Infrastructure.Database.Repositories;

public class BaseRepository<T> : IBaseRepository<T> where T : BaseEntity
{
    protected readonly ApplicationContext _applicationContext;

    public BaseRepository(ApplicationContext applicationContext)
    {
        _applicationContext = applicationContext;
    }

    public async Task<List<T>> GetAll(CancellationToken cancellationToken)
    {
        return await _applicationContext.Set<T>().Where(x => !x.Deleted).ToListAsync(cancellationToken);
    }

    public async Task<Paged<T>> GetAllPaged(int page, int pageSize, CancellationToken cancellationToken)
    {
        var query = _applicationContext.Set<T>().Where(x => !x.Deleted);
        
        var totalCount = await query.CountAsync(cancellationToken);
        var data = await query
            .Skip((page - 1) * pageSize)
            .Take(pageSize)
            .ToListAsync(cancellationToken);

        return new Paged<T>
        {
            Data = data,
            TotalCount = totalCount
        };
    }
    
    public async Task<T?> GetBy(Expression<Func<T, bool>> predicate, CancellationToken cancellationToken, params Expression<Func<T, object>>[] includes)
    {
        var query = _applicationContext.Set<T>().AsQueryable();

        if (includes?.Length > 0)
            foreach (var include in includes)
                query = query.Include(include);

        return await query.FirstOrDefaultAsync(predicate, cancellationToken);
    }

    public async Task<List<T>> GetByIds(List<Guid> ids, CancellationToken cancellationToken)
    {
        return await _applicationContext.Set<T>()
            .Where(x => ids.Contains(x.Id) && !x.Deleted)
            .ToListAsync(cancellationToken);
    }

    public Task<T> Create(T entity, CancellationToken cancellationToken)
    {
        var now = DateTime.UtcNow;

        entity.Id = Guid.NewGuid();
        entity.CreatedAt = now;
        entity.UpdatedAt = now;
        _applicationContext.Add(entity);
        return Task.FromResult(entity);
    }

    public Task<T> Update(T entity, CancellationToken cancellationToken)
    {
        entity.UpdatedAt = DateTime.UtcNow;
        _applicationContext.Update(entity);
        return Task.FromResult(entity);
    }

    public Task Delete(T entity, CancellationToken cancellationToken)
    {
        entity.Deleted = true;
        entity.UpdatedAt = DateTime.UtcNow;
        _applicationContext.Update(entity);
        return Task.CompletedTask;
    }

}
