package model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StoreOrder> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ShoppingCartItem> cartItems;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_favorites",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> favoriteProducts;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<StoreOrder> getOrders() { return orders; }
    public void setOrders(List<StoreOrder> orders) { this.orders = orders; }

    public List<ShoppingCartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<ShoppingCartItem> cartItems) { this.cartItems = cartItems; }

    public Set<Product> getFavoriteProducts() { return favoriteProducts; }
    public void setFavoriteProducts(Set<Product> favoriteProducts) { this.favoriteProducts = favoriteProducts; }
}
