package model;

import jakarta.persistence.*;
import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}