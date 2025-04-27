package dao;

import jakarta.enterprise.context.RequestScoped;
import model.StoreOrder;

@RequestScoped
public class StoreOrderDAO extends BaseDAO<StoreOrder> {
    public StoreOrderDAO() {
        super(StoreOrder.class);
    }
}
