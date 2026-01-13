package com.azs.cashdesk.controller;

import com.azs.cashdesk.model.Fuel;
import com.azs.cashdesk.model.Product;
import com.azs.cashdesk.service.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private GasStationService service;

    // --- СТРАНИЦЫ ---
    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fuels", service.getFuels());
        model.addAttribute("products", service.getProducts());
        model.addAttribute("cart", service.getCart());
        model.addAttribute("cartTotal", service.getCartTotal());
        model.addAttribute("history", service.getOrderHistory());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("fuels", service.getFuels());
        model.addAttribute("products", service.getProducts());
        return "admin";
    }

    // --- API ДЛЯ SWING ---
    @GetMapping("/api/fuels")
    @ResponseBody
    public List<Fuel> getFuelsApi() { return service.getFuels(); }

    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProductsApi() { return service.getProducts(); }

    // --- УПРАВЛЕНИЕ ТОПЛИВОМ (БРАУЗЕР + SWING) ---
    @PostMapping("/admin/add-fuel")
    public String addFuel(@RequestParam String type, @RequestParam double price) {
        service.addFuel(type, price);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-fuel") // ЭТОТ МЕТОД ЛЕЧИТ 404
    public String deleteFuel(@RequestParam Long id) {
        service.deleteFuel(id);
        return "redirect:/admin";
    }

    // --- УПРАВЛЕНИЕ ТОВАРАМИ (БРАУЗЕР + SWING) ---
    @PostMapping("/admin/add-product")
    public String addProduct(@RequestParam String name, @RequestParam double price) {
        service.addProduct(name, price);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-product") // ДЛЯ УДАЛЕНИЯ ТОВАРОВ
    public String deleteProduct(@RequestParam Long id) {
        service.deleteProduct(id);
        return "redirect:/admin";
    }

    // --- КАССОВЫЕ ОПЕРАЦИИ ---
    @PostMapping("/sell-fuel")
    public String sellFuel(@RequestParam Long fuelId, @RequestParam double liters) {
        service.sellFuel(fuelId, liters);
        return "redirect:/";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        service.addToCart(productId, quantity);
        return "redirect:/";
    }

    @PostMapping("/checkout")
    public String checkout() {
        service.checkout();
        return "redirect:/";
    }
}