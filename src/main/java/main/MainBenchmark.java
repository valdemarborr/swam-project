package main;

import dao.*;
import model.*;
import util.EntityManagerProducer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * MainBenchmark.java
 *
 * Benchmark-fokusert variant som oppretter 1 000 brukere, og gir hver av dem 10 ordrer,
 * alle med samme DigitalProduct.
 *
 * Etter å ha lagt inn dataene, måler den tiden det tar å hente alle brukerne og
 * aksessere relaterte data: brukerens ordrer (OneToMany), favorittprodukter (ManyToMany),
 * og adresse (Embedded).
 *
 * Dette gjør det mulig å teste effekten av fetch-type (EAGER vs LAZY), og embedded versus
 * relasjonsbasert data, samt hvorvidt @ManyToMany/OneToMany koster mye under lasting.
 *
 * Brukes for å benchmarke henteytelse og vurdere effektiviteten av mapping-konfigurasjonene.
 */


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

        DigitalProduct commonProduct = new DigitalProduct();
        commonProduct.setName("Standard Digital Product");
        commonProduct.setPrice(new BigDecimal("10.0"));
        productDAO.save(commonProduct);

        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("User_" + i);
            Address address = new Address();
            address.setCity("City_" + i);
            address.setStreet("Street_" + i);
            address.setZipCode("ZIP_" + i);
            user.setAddress(address);
            userDAO.save(user);

            for (int j = 0; j < 10; j++) {
                StoreOrder order = new StoreOrder();
                order.setOrderDetails("Order_" + j + "_for_User_" + i);
                order.setUser(user);
                order.setProducts(List.of(new OrderLine(commonProduct, 1)));
                storeOrderDAO.save(order);
            }
        }

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}