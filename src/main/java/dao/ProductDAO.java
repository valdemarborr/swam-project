package dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class ProductDAO extends BaseDAO<model.Product> {
    public ProductDAO() {
        super(model.Product.class);
    }
}