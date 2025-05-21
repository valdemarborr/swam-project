package dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class CategoryDAO extends BaseDAO<model.Category> {
    public CategoryDAO() {
        super(model.Category.class);
    }
}
