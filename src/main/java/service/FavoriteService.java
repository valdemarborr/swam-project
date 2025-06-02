package service;

import dao.*;
import model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

@RequestScoped
public class FavoriteService {

    @Inject
    private FavoriteProductDAO favoriteProductDAO;

    @Inject
    private ProductDAO productDAO;

    @Inject
    private UserDAO userDAO;

    public void addFavorite(Long userId, Long productId) {
        User user = userDAO.findById(userId);
        Product product = productDAO.findById(productId);
        FavoriteProduct favorite = new FavoriteProduct(user, product);
        favoriteProductDAO.save(favorite);
    }

    public List<FavoriteProduct> getFavoritesByUser(Long userId) {
        return favoriteProductDAO.findByUserId(userId);
    }
}

