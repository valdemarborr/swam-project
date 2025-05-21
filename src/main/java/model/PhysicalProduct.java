package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
public class PhysicalProduct extends Product {
    private double weight;
    private String dimensions;
    // Getters and setters
}