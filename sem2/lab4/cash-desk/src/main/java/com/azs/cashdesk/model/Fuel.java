package com.azs.cashdesk.model;
import jakarta.persistence.*;

@Entity
public class Fuel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private double price;
    private double stock;

    public Fuel() {}
    public Fuel(String type, double price, double stock) {
        this.type = type; this.price = price; this.stock = stock;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}