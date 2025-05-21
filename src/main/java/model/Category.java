package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> subcategories;

    // Getters and setters
}