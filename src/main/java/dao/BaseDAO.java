package dao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class BaseDAO<T> {
    @Inject
    public EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
