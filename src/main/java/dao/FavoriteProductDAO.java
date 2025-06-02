package dao;

import java.util.List;


import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class FavoriteProductDAO extends BaseDAO<model.FavoriteProduct> {
    public FavoriteProductDAO() {
        super(model.FavoriteProduct.class);
    }

    public List<model.FavoriteProduct> findByUserId(Long userId) {
        return this.entityManager.createQuery("SELECT f FROM FavoriteProduct f WHERE f.user.id = :userId", model.FavoriteProduct.class)
                 .setParameter("userId", userId)
                 .getResultList();
    }
}

