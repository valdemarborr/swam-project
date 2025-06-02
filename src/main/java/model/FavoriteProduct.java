package model;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class FavoriteProduct {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private LocalDateTime markedAt;

    // getters/setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getMarkedAt() {
        return markedAt;
    }


    public FavoriteProduct(User user, Product product) {
        this.user = user;
        this.product = product;
        this.markedAt = LocalDateTime.now();
    }
}
