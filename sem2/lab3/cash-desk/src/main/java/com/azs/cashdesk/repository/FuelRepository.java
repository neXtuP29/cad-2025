package com.azs.cashdesk.repository;

import com.azs.cashdesk.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
    // Здесь пусто, так как базовых методов (save, findAll, findById) нам достаточно
}