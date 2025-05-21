package model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;
    private String country;
    // Getters and setters
}
