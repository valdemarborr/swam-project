package service;

import dao.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.*;

import java.util.List;

@RequestScoped
public class CartService {
    @Inject
    ShoppingCartItemDAO shoppingCartItemDAO;

    @Inject
    ProductDAO productDAO;

    @Inject
    UserDAO userDAO;

    @Transactional
    public void addToCart(Long userId, Long productId, int quantity) {
        User user = userDAO.findById(userId);
        Product product = productDAO.findById(productId);

        ShoppingCartItem item = new ShoppingCartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        shoppingCartItemDAO.save(item);
    }

    public List<ShoppingCartItem> getUserCart(Long userId) {
        User user = userDAO.findById(userId);
        return user.getCartItems();
    }
}
