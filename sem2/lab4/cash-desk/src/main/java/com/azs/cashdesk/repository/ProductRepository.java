package com.azs.cashdesk.repository;

import com.azs.cashdesk.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Здесь тоже пусто, Spring всё сделает за нас
}