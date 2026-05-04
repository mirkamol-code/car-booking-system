package com.mirkamolcode.repository;

import com.mirkamolcode.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    List<Car> findByRegNumber(String regNumber);

    List<Car> findByIsElectricTrue();

    List<Car> findByIsAvailableTrue();

    @Query("SELECT c FROM Car c WHERE c.isAvailable = true AND c.isElectric = true")
    List<Car> findByAvailableElectricCars();

    @Query("SELECT c FROM Car c WHERE c.isAvailable = true AND c.id = ?1")
    Optional<Car> findAvailableCarById(UUID carId);
}
