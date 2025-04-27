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

        UserDAO userDAO = new UserDAO();
        StoreOrderDAO storeOrderDAO = new StoreOrderDAO();
        ItemDAO itemDAO = new ItemDAO();
        userDAO.entityManager = em;
        storeOrderDAO.entityManager = em;
        itemDAO.entityManager = em;

        em.getTransaction().begin();

        // Lag en Item som alle bestiller
        Item commonItem = new Item();
        commonItem.setName("MagicMouse");
        commonItem.setPrice(59.99);
        itemDAO.save(commonItem);

        // Bulk insert: 100 brukere med 2 bestillinger hver
        List<User> users = new ArrayList<>();
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
            users.add(user);
        }

        em.getTransaction().commit();

        // Benchmarking starts here
        System.out.println("\n--- Benchmark Fetching Users and their Orders ---");

        long start = System.currentTimeMillis();
        List<User> fetchedUsers = userDAO.findAll(); // Fetch Users (Orders lazy-loaded)
        for (User u : fetchedUsers) {
            // Force loading orders
            if (u.getStoreOrders() != null) {
                u.getStoreOrders().size(); // Trigger lazy load
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Tid brukt på å hente 100 Users + Orders: " + (end - start) + " ms");

        em.close();
        emf.close();
    }
}

