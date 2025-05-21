package dao;

import jakarta.enterprise.context.RequestScoped;
import model.StoreOrder;

@RequestScoped
public class StoreOrderDAO extends BaseDAO<model.StoreOrder> {
    public StoreOrderDAO() {
        super(model.StoreOrder.class);
    }
}