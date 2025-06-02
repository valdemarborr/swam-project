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

        long fetchStart = System.currentTimeMillis(); // Start tidtaking
        EntityManagerFactory emf = new EntityManagerProducer().produceEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserDAO userDAO = new UserDAO();
        StoreOrderDAO storeOrderDAO = new StoreOrderDAO();
        ProductDAO productDAO = new ProductDAO();

        userDAO.entityManager = em;
        storeOrderDAO.entityManager = em;
        productDAO.entityManager = em;

        em.getTransaction().begin();

        for (int i = 0; i < 100; i++) {
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

        int fetchRuns = 1; // Antall ganger loopen kjøres for å forsterke effekt
        long fetch = System.currentTimeMillis(); // Start tidtaking

        for (int run = 0; run < fetchRuns; run++) {

            // Hent alle brukere fra databasen (kun brukerne, ikke relasjonene automatisk ved LAZY)
            List<User> allUsers = em.createQuery("SELECT u FROM User u", User.class).getResultList();

            for (User user : allUsers) {

                // Tilgang til adressefeltet (enten @Embedded eller @OneToOne)
                // Trigger en eventuell LAZY lasting dersom OneToOne
                if (user.getAddress() != null) {
                    user.getAddress().getCity(); // Leser et felt for å sikre lasting
                }

                // // Hent alle handlekurv-elementer knyttet til brukeren
                // // Ved LAZY må det faktisk itereres for at de skal hentes
                List<ShoppingCartItem> cartItems = user.getCartItems();
                for (ShoppingCartItem item : cartItems) {
                    Product product = item.getProduct(); // Hent produkt for hvert handlekurv-element
                    if (product != null) {
                        product.getName(); // Trigger lasting av produktinfo
                    }
                }

                // // Hent alle ordrer knyttet til brukeren
                List<StoreOrder> orders = user.getOrders();
                for (StoreOrder order : orders) {
                    List<OrderLine> lines = order.getProducts(); // Hent ordrelinjer
                    for (OrderLine line : lines) {
                        Product product = line.getProduct(); // Hent produkt i ordrelinjen
                        if (product != null) {
                            product.getName(); // Trigger fetching av produktet
                        }
                    }
                }
            }
        }

        long fetchEnd = System.currentTimeMillis(); // Slutt tidtaking
        System.out.println("Complete runtime with " + fetchRuns + " fetchloops: " + (fetchEnd - fetchStart) + " ms");

        long fetch1 = System.currentTimeMillis(); // Slutt tidtaking
        System.out.println("Runtime of " + fetchRuns + " fetchloops only: " + (fetch1 - fetch) + " ms");
        // Ferdig med transaksjonen
        // Ferdig med transaksjonen
        em.getTransaction().commit();


        em.close();
        emf.close();
    }
}