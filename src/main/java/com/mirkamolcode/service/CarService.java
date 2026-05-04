package com.mirkamolcode.service;

import com.mirkamolcode.dto.CarResponse;
import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.Car;
import com.mirkamolcode.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mirkamolcode.model.enums.ResponseMessage.*;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public List<CarResponse> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getRegNumber(),
                        car.getRentalPricePerDay(),
                        car.getBrand(),
                        car.isElectric(),
                        car.isAvailable()
                )).toList();
    }

    public List<CarResponse> getAllAvailableCars() {
        return carRepository.findByIsAvailableTrue()
                .stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getRegNumber(),
                        car.getRentalPricePerDay(),
                        car.getBrand(),
                        car.isElectric(),
                        car.isAvailable()
                )).toList();
    }

    public List<CarResponse> getElectricCars() {
        return carRepository.findByIsElectricTrue().stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getRegNumber(),
                        car.getRentalPricePerDay(),
                        car.getBrand(),
                        car.isElectric(),
                        car.isAvailable()
                ))
                .toList();
    }

    public List<CarResponse> getAvailableElectricCars() {
        return carRepository.findByAvailableElectricCars().stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getRegNumber(),
                        car.getRentalPricePerDay(),
                        car.getBrand(),
                        car.isElectric(),
                        car.isAvailable()
                ))
                .toList();
    }

    public Car getCarById(UUID id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND.getMessage()));
    }
    public Car getAvailableCarById(UUID id) {
        return carRepository
                .findAvailableCarById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND.getMessage()));
    }

    public Car bookCarById(UUID id) {
        Car car = carRepository.findAvailableCarById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CAR_NOT_FOUND.getMessage()));
        car.setAvailable(false);
        carRepository.save(car);
        return car;
    }
}
