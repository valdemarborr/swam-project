package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
public class DigitalProduct extends Product {
    private String downloadUrl;
    private double sizeInMb;
    // Getters and setters
}