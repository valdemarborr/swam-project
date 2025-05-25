package main;

import model.User;
import model.StoreOrder;
import model.OrderLine;
import model.Product;
import dao.UserDAO;
// import model.ShoppingCartItem;

import util.EntityManagerProducer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;


public class FetchOnlyMain {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        EntityManagerFactory emf = new EntityManagerProducer().produceEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        long fetchStart = System.currentTimeMillis();

        int fetchRuns = 50;
        UserDAO userDAO = new UserDAO();
        for (int run = 0; run < fetchRuns; run++) {
            List<User> allUsers = userDAO.findAll();

            for (User user : allUsers) {
                // Trigger LAZY lasting
                user.getAddress().getCity();

                // Hent cart items
                // user.getCartItems().forEach(item -> {
                //     Product p = item.getProduct();
                //     if (p != null) p.getName();
                // });

                // Hent ordrer
                // user.getOrders().forEach(order -> {
                //     order.getProducts().forEach(line -> {
                //         Product p = line.getProduct();
                //         if (p != null) p.getName();
                //     });
                // });
            }
        }

        long fetchEnd = System.currentTimeMillis();
        System.out.println("🕒 Fetch runtime (" + fetchRuns + " loops): " + (fetchEnd - fetchStart) + " ms");

        em.close();
        emf.close();
    }
}
