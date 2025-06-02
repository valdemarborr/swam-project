package dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class ShoppingCartItemDAO extends BaseDAO<model.ShoppingCartItem> {
    public ShoppingCartItemDAO() {
        super(model.ShoppingCartItem.class);
    }

    public List<model.ShoppingCartItem> findByUserId(Long userId) {
        return this.entityManager.createQuery("SELECT s FROM ShoppingCartItem s WHERE s.user.id = :userId", model.ShoppingCartItem.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}