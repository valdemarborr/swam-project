package dao;

import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class StoreOrderDAO extends BaseDAO<model.StoreOrder> {
    public StoreOrderDAO() {
        super(model.StoreOrder.class);
    }

    public List<model.StoreOrder> findByUserId(Long userId) {
        return this.entityManager.createQuery("SELECT o FROM StoreOrder o WHERE o.user.id = :userId", model.StoreOrder.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}