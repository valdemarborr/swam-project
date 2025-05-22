package model;

import jakarta.persistence.*;

@Embeddable
public class OrderLine {

    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderLine() {}

    public OrderLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and setters

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
