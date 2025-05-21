package dao;

import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class ReviewDAO extends BaseDAO<model.Review> {
    public ReviewDAO() {
        super(model.Review.class);
    }
}