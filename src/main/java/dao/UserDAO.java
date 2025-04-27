package dao;

import jakarta.enterprise.context.RequestScoped;
import model.User;

@RequestScoped
public class UserDAO extends BaseDAO<User> {
    public UserDAO() {
        super(User.class);
    }
}
