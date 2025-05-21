package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
public class StoreOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> items;

    @Embedded
    private PaymentInfo paymentInfo;

    public void setUser(User user2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUser'");
    }

    public void setItem(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setItem'");
    }

    public void setOrderDetails(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOrderDetails'");
    }

    // Getters and setters
}