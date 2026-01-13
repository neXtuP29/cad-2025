package com.azs.cashdesk.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String details;
    private double totalSum;
    private LocalDateTime timestamp;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public double getTotalSum() { return totalSum; }
    public void setTotalSum(double totalSum) { this.totalSum = totalSum; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}