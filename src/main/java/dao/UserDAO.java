package dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import model.User;

@RequestScoped
public class UserDAO extends BaseDAO<User> {
    public UserDAO() {
        super(User.class);
    }

    public List<User> findAllWithOrders() {
        return entityManager.createQuery(
            "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.storeOrders", User.class
        ).getResultList();
    }
    
}
