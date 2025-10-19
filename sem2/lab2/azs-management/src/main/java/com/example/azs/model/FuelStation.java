package com.example.azs.model;

public class FuelStation {
    private static Long nextId = 1L;
    private Long id;
    private String name;
    private String address;
    private String phone;
    private boolean active;

    public FuelStation() {
        this.id = generateId();
        this.active = true;
    }

    public FuelStation(String name, String address, String phone) {
        this.id = generateId();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.active = true;
    }

    private synchronized Long generateId() {
        return nextId++;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}