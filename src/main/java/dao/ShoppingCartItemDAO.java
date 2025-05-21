package dao;

import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class ShoppingCartItemDAO extends BaseDAO<model.ShoppingCartItem> {
    public ShoppingCartItemDAO() {
        super(model.ShoppingCartItem.class);
    }
}