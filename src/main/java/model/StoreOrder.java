package model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class StoreOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String OrderDetails;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> products;

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
    public User getBuyer() {
        return buyer;
    }
    public void setUser(User user) {
        this.buyer = user;
    }
    public List<OrderLine> getProducts() {
        return products;
    }
    public void setProducts(List<OrderLine> products) {
        this.products = products;
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

        
}