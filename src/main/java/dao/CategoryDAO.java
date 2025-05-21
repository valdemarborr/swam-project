package dao;

import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class CategoryDAO extends BaseDAO<model.Category> {
    public CategoryDAO() {
        super(model.Category.class);
    }
}
