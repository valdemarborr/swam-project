package service;

import dao.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.*;

import java.util.List;
@RequestScoped
public class OrderService {
    @Inject
    StoreOrderDAO storeOrderDAO;

    @Inject
    ShoppingCartItemDAO shoppingCartItemDAO;

    @Inject
    UserDAO userDAO;

    @Transactional
    public void checkout(Long userId, PaymentInfo paymentInfo) {
        User user = userDAO.findById(userId);

        StoreOrder order = new StoreOrder();
        order.setUser(user);
        order.setStatus("CONFIRMED");
        order.setPaymentInfo(paymentInfo);

        List<ShoppingCartItem> cartItems = shoppingCartItemDAO.findByUserId(userId);
        for (ShoppingCartItem cartItem : cartItems) {
            OrderLine line = new OrderLine();
            line.setProduct(cartItem.getProduct());
            line.setQuantity(cartItem.getQuantity());
            line.setOrder(order);
            order.getProducts().add(line);
            shoppingCartItemDAO.delete(cartItem);
        }

        storeOrderDAO.save(order);
    }

    public List<StoreOrder> getOrdersByUser(Long userId) {
        return storeOrderDAO.findByUserId(userId);
    }
}
