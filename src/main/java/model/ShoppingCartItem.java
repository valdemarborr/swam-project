package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity

public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private int quantity;

    // Getters and setters
}
