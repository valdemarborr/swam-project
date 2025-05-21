package dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import model.User;

@RequestScoped
public class UserDAO extends BaseDAO<model.User> {
    public UserDAO() {
        super(model.User.class);
    }
}