package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class MainBenchmark {
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

        Product commonProduct = new DigitalProduct();
        commonProduct.setName("Standard Product");
        commonProduct.setPrice(new BigDecimal("10.0"));
        productDAO.save(commonProduct);

        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("User_" + i);
            Address address = new Address();
            address.setCity("City_" + i);
            address.setStreet("Street_" + i);
            address.setZipCode("ZIP_" + i);
            user.setAddress(address);
            userDAO.save(user);

            for (int j = 0; j < 2; j++) {
                StoreOrder order = new StoreOrder();
                order.setOrderDetails("Order_" + j + "_for_User_" + i);
                order.setUser(user);
                order.setProducts(List.of(new OrderLine(commonProduct, 1)));
                storeOrderDAO.save(order);
            }
        }

        em.getTransaction().commit();

        long start = System.currentTimeMillis();
        List<User> fetchedUsers = userDAO.findAll();
        // for (User u : fetchedUsers) {
        //     if (u.getStoreOrders() != null) {
        //         u.getStoreOrders().size();
        //     }
        // }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

        em.close();
        emf.close();
    }
}
