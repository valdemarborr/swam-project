package model;

import jakarta.persistence.*;

@Entity
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Category category;


    public ProductCategory() {}



}
