package dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class ReviewDAO extends BaseDAO<model.Review> {
    public ReviewDAO() {
        super(model.Review.class);
    }
}