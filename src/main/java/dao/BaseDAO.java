package dao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

public abstract class BaseDAO<T> {
    @Inject
    public EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }
} 
