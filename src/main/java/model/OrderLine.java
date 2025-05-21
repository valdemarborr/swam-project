package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private StoreOrder order;

    // Getters and setters
}