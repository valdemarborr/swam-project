package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Embeddable
public class PaymentInfo {
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;
    // Getters and setters
}