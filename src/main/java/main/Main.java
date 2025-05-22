package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Main.java
 *
 * Denne klassen simulerer en intensiv lagrings- og relasjonsoppbygging i databasen.
 * Den oppretter 10 000 brukere. Hver bruker får 10 000 produkter (DigitalProduct eller PhysicalProduct)
 * som legges i handlekurven (ShoppingCartItem). I tillegg genereres en ordre (StoreOrder) per bruker
 * med 5 produkter.
 *
 * Målet er å generere høy datamengde og mye databaseaktivitet for å kunne studere
 * ytelseseffekter av ulike JPA-mappingstrategier (f.eks. LAZY vs EAGER, @Embedded typer).
 *
 * Nyttig for: å bruke VisualVM for å observere minnebruk, GC, database-load og CPU-belastning.
 */

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

        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("User_" + i);
            Address address = new Address();
            address.setCity("City_" + i);
            address.setStreet("Street_" + i);
            address.setZipCode("ZIP_" + i);
            user.setAddress(address);
            userDAO.save(user);

            List<Product> userProducts = new ArrayList<>();

            for (int j = 0; j < 100; j++) {
                Product product;
                if (j % 2 == 0) {
                    product = new DigitalProduct();
                } else {
                    product = new PhysicalProduct();
                }
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
            for (int k = 0; k < 5 && k < userProducts.size(); k++) {
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
<<<<<<< HEAD
                long start = System.currentTimeMillis();
        List<User> fetchedUsers = userDAO.findAll();
        for (User u : fetchedUsers) {
            u.getStoreOrders().size(); // trigger LAZY or EAGER
            u.getFavoriteProducts().size();
            if (u.getAddress() != null) {
                u.getAddress().getCity();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
=======
>>>>>>> cf151856756438fd081fb724f3b94345bf615320
        em.close();
        emf.close();
    }
}