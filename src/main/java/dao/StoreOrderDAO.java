package dao;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class StoreOrderDAO extends BaseDAO<model.StoreOrder> {
    public StoreOrderDAO() {
        super(model.StoreOrder.class);
    }
}