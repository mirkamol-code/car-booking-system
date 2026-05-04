package com.mirkamolcode.controller;

import com.mirkamolcode.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailableCars(){
        return ResponseEntity.ok(carService.getAllAvailableCars());
    }

    @GetMapping("/available/electric")
    public ResponseEntity<?> getElectricCars(){
        return ResponseEntity.ok(carService.getAvailableElectricCars());
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<?> getAvailableCarById(@PathVariable UUID id){
        return ResponseEntity.ok(carService.getAvailableCarById(id));
    }


}
