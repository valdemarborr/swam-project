package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(String[] args) {
        // Manuelt for testing uten full server/CDI container
        EntityManagerFactory emf = new EntityManagerProducer().produceEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Sett opp DAOer
        UserDAO userDAO = new UserDAO();
        StoreOrderDAO storeOrderDAO = new StoreOrderDAO();
        ItemDAO itemDAO = new ItemDAO();

        userDAO.entityManager = em;
        storeOrderDAO.entityManager = em;
        itemDAO.entityManager = em;

        // Start transaksjon
        em.getTransaction().begin();

        // Lag en Item
        Item item = new Item();
        item.setName("Laptop");
        item.setPrice(999.99);
        itemDAO.save(item);

        // Lag en User
        User user = new User();
        user.setName("Valdemar");
        Address address = new Address();
        address.setCity("Firenze");
        address.setStreet("Via Roma");
        address.setZipcode("50100");
        user.setAddress(address);
        userDAO.save(user);

        // Lag en StoreOrder
        StoreOrder order = new StoreOrder();
        order.setUser(user);
        order.setItem(item);
        order.setOrderDetails("Express shipping");
        storeOrderDAO.save(order);

        em.getTransaction().commit();

        // Hent og skriv ut
        System.out.println("User hentet: " + userDAO.findById(user.getId()).getName());

        em.close();
        emf.close();
    }
}
