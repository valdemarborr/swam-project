package service;

import dao.StoreOrderDAO;
import dao.ItemDAO;
import dao.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import model.Item;
import model.StoreOrder;
import model.User;

@RequestScoped
public class OrderService {
    @Inject
    private StoreOrderDAO storeOrderDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ItemDAO itemDAO;

    public void createOrder(Long userId, Long itemId, String orderDetails) {
        User user = userDAO.findById(userId);
        Item item = itemDAO.findById(itemId);

        StoreOrder order = new StoreOrder();
        order.setUser(user);
        order.setItem(item);
        order.setOrderDetails(orderDetails);

        storeOrderDAO.save(order);
    }
}
