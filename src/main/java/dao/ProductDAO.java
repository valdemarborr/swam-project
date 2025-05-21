package dao;

import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class ProductDAO extends BaseDAO<model.Product> {
    public ProductDAO() {
        super(model.Product.class);
    }
}