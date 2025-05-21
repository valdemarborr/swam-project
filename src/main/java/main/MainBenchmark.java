package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class MainBenchmark {

    public static void main(String[] args) {
        EntityManagerFactory emf = new EntityManagerProducer().produceEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Setup DAOs with current EntityManager
        UserDAO userDAO = new UserDAO();
        StoreOrderDAO storeOrderDAO = new StoreOrderDAO();
        ItemDAO itemDAO = new ItemDAO();
        userDAO.entityManager = em;
        storeOrderDAO.entityManager = em;
        itemDAO.entityManager = em;

        // Populate database: 100 users × 2 orders each
        em.getTransaction().begin();

        Item commonItem = new Item();
        commonItem.setName("MagicMouse");
        commonItem.setPrice(59.99);
        itemDAO.save(commonItem);

        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("User_" + i);

            Address address = new Address();
            address.setCity("City_" + i);
            address.setStreet("Street_" + i);
            address.setZipcode("ZIP_" + i);
            user.setAddress(address);

            userDAO.save(user);

            for (int j = 0; j < 2; j++) {
                StoreOrder order = new StoreOrder();
                order.setOrderDetails("Order_" + j + "_for_User_" + i);
                order.setUser(user);
                order.setItem(commonItem);
                storeOrderDAO.save(order);
            }
        }

        em.getTransaction().commit();

        // Clear persistence context to force fresh SQL fetches
        em.clear();

        System.out.println("\n--- Benchmark: Fetching Users and their Orders ---");

        long start = System.currentTimeMillis();

        // Fetch users from DB
        // List<User> fetchedUsers = userDAO.findAllWithOrders(); // Eager
        List<User> fetchedUsers = userDAO.findAll(); // Lazy


        // Trigger order fetching (only matters with LAZY)
        for (User u : fetchedUsers) {
            if (u.getStoreOrders() != null) {
                ((List<User>) u.getStoreOrders()).size(); // Forces lazy loading if applicable
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Loaded users: " + fetchedUsers.size());
        System.out.println("Time taken to fetch users + orders: " + (end - start) + " ms");

        em.close();
        emf.close();
    }
}
