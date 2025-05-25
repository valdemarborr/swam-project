package main;

import dao.UserDAO;
import dao.ProductDAO;
import dao.StoreOrderDAO;
import model.User;
import model.Address;
import model.Product;
import model.DigitalProduct;
import model.PhysicalProduct;
import model.ShoppingCartItem;
import model.OrderLine;
import model.StoreOrder;
import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class DataGeneratorMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = new EntityManagerProducer().produceEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserDAO userDAO = new UserDAO();
        StoreOrderDAO storeOrderDAO = new StoreOrderDAO();
        ProductDAO productDAO = new ProductDAO();

        userDAO.entityManager = em;
        storeOrderDAO.entityManager = em;
        productDAO.entityManager = em;

        em.getTransaction().begin();

        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("User_" + i);
            Address address = new Address("City_" + i, "Street_" + i, "ZIP_" + i);
            user.setAddress(address);
            userDAO.save(user);

            List<Product> userProducts = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                Product product = (j % 2 == 0) ? new DigitalProduct() : new PhysicalProduct();
                product.setName("Product_" + i + "_" + j);
                product.setPrice(new BigDecimal("19.99"));
                productDAO.save(product);
                userProducts.add(product);

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(1);
                item.setUser(user);
                em.persist(item);

                if (j % 100 == 0) {
                    em.flush();
                    em.clear();
                }
            }

            List<OrderLine> orderLines = new ArrayList<>();
            for (int k = 0; k < 5; k++) {
                orderLines.add(new OrderLine(userProducts.get(k), 2));
            }

            StoreOrder order = new StoreOrder();
            order.setUser(user);
            order.setProducts(orderLines);
            order.setOrderDetails("Express shipping for user " + i);
            storeOrderDAO.save(order);

            if (i % 100 == 0) {
                System.out.println("Processed user " + i);
                em.flush();
                em.clear();
            }
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("✅ Data generation complete.");
    }
}
