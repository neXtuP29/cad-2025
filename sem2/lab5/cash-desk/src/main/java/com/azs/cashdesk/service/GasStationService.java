package com.azs.cashdesk.service;

import com.azs.cashdesk.model.*;
import com.azs.cashdesk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GasStationService {
    @Autowired private FuelRepository fuelRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;

    // Методы получения данных
    public List<Fuel> getFuels() { return fuelRepository.findAll(); }
    public List<Product> getProducts() { return productRepository.findAll(); }
    public List<Order> getOrderHistory() { return orderRepository.findAllByOrderByTimestampDesc(); }

    // --- ТОПЛИВО ---
    @Transactional
    public void addFuel(String type, double price) {
        fuelRepository.save(new Fuel(type, price, 1000.0));
    }

    @Transactional
    public void deleteFuel(Long id) {
        fuelRepository.deleteById(id);
    }

    @Transactional
    public void updateFuelPrice(Long id, double price) {
        fuelRepository.findById(id).ifPresent(f -> { f.setPrice(price); fuelRepository.save(f); });
    }

    // --- ТОВАРЫ ---
    @Transactional
    public void addProduct(String name, double price) {
        productRepository.save(new Product(name, price));
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProductPrice(Long id, double price) {
        productRepository.findById(id).ifPresent(p -> { p.setPrice(price); productRepository.save(p); });
    }

    // Методы корзины (оставляем без изменений)
    private List<String> cart = new java.util.ArrayList<>();
    private double cartTotal = 0;
    public List<String> getCart() { return cart; }
    public double getCartTotal() { return cartTotal; }
    public void addToCart(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(p -> {
            cart.add(p.getName() + " x" + quantity);
            cartTotal += p.getPrice() * quantity;
        });
    }
    @Transactional
    public void checkout() {
        if (cart.isEmpty()) return;
        Order o = new Order();
        o.setTitle("Магазин"); o.setDetails(String.join(", ", cart));
        o.setTotalSum(cartTotal); o.setTimestamp(java.time.LocalDateTime.now());
        orderRepository.save(o);
        cart.clear(); cartTotal = 0;
    }
    @Transactional
    public void sellFuel(Long fuelId, double liters) {
        fuelRepository.findById(fuelId).ifPresent(f -> {
            Order o = new Order();
            o.setTitle("Заправка"); o.setDetails(f.getType() + " (" + liters + " л)");
            o.setTotalSum(f.getPrice() * liters); o.setTimestamp(java.time.LocalDateTime.now());
            orderRepository.save(o);
        });
    }
}