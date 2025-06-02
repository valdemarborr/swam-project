package dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ProductCategoryDAO extends BaseDAO<model.ProductCategory> {
    public ProductCategoryDAO() {
        super(model.ProductCategory.class);
    }

    public List <model.Product> findProductsByCategoryId(Long categoryId) {
        return this.entityManager.createQuery(
                "SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId", model.Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}
