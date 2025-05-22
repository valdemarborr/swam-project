package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class Main {
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

        Product product = new DigitalProduct();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));
        productDAO.save(product);

        User user = new User();
        user.setName("Valdemar");
        Address address = new Address();
        address.setCity("Firenze");
        address.setStreet("Via Roma");
        address.setZipCode("50100");
        user.setAddress(address);
        userDAO.save(user);
        em.flush();

        for (int j = 0; j < 1000000; j++) {
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(1);
            item.setUser(user);

            em.persist(item);

            // Flush og clear med jevne mellomrom for å unngå OutOfMemoryError
            if (j % 100 == 0) {
                em.flush();
                em.clear();
            }
        }


        StoreOrder order = new StoreOrder();
        order.setUser(user);
        order.setProducts(List.of(new OrderLine(product, 1)));
        order.setOrderDetails("Express shipping");
        storeOrderDAO.save(order);

        em.getTransaction().commit();

        System.out.println("User hentet: " + userDAO.findById(user.getId()).getName());

        em.close();
        emf.close();
    }
}