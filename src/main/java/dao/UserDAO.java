package dao;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UserDAO extends BaseDAO<model.User> {
    public UserDAO() {
        super(model.User.class);
    }
}