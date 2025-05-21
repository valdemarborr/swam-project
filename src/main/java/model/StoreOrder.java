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
    private String OrderDetails;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> items;

    @Embedded
    private PaymentInfo paymentInfo;

    // Getters and setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<OrderLine> getItem() {
        return items;
    }
    public void setItem(Item item) {
        this.items = (List<OrderLine>) item;
    }
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
    public String getOrderDetails() {
        return OrderDetails;
    }   
    public void setOrderDetails(String orderDetails) {
        OrderDetails = orderDetails;
    }

    public List<OrderLine> getItems() {
        return items;
    }

    public void setItems(List<OrderLine> items) {
        this.items = items;
    }
        
}