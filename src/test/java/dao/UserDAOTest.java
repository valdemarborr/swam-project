package dao;

import model.Address;
import model.User;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import util.EntityManagerProducer;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private UserDAO userDAO;

    @BeforeAll
    void setup() {
        emf = new EntityManagerProducer().produceEntityManagerFactory();
    }

    @BeforeEach
    void init() {
        em = emf.createEntityManager();
        userDAO = new UserDAO();
        userDAO.entityManager = em;
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) em.close();
    }

    @AfterAll
    void shutdown() {
        if (emf.isOpen()) emf.close();
    }

    @Test
    void testSaveAndFind() {
        em.getTransaction().begin();

        User user = new User();
        user.setName("TestUser");
        Address address = new Address();
        address.setCity("TestCity");
        address.setStreet("TestStreet");
        address.setZipcode("12345");
        user.setAddress(address);

        userDAO.save(user);
        Long userId = user.getId();

        em.getTransaction().commit();

        User foundUser = userDAO.findById(userId);

        assertNotNull(foundUser);
        assertEquals("TestUser", foundUser.getName());
    }
}
