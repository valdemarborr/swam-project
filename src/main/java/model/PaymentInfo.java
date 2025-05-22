package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
public class PaymentInfo {
    @Id
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;
    // Getters and setters
}