package service;

import java.util.List;

import dao.ProductCategoryDAO;
import model.Product;

public class CategoryService {
    private ProductCategoryDAO productCategoryDAO;

    public List<Product> getProductsInCategory(Long categoryId) {
        return productCategoryDAO.findProductsByCategoryId(categoryId);
}

}
