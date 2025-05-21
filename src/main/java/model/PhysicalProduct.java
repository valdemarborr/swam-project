package model;

import jakarta.persistence.*;



@Entity
public class PhysicalProduct extends Product {
    private double weight;
    private String dimensions;

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}