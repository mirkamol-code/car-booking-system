package com.mirkamolcode.service;

import com.mirkamolcode.dto.CarBookingRequest;
import com.mirkamolcode.dto.CarBookingResponse;
import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.AppUser;
import com.mirkamolcode.model.Car;
import com.mirkamolcode.model.CarBooking;
import com.mirkamolcode.model.enums.BookingStatus;
import com.mirkamolcode.repository.CarBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mirkamolcode.model.enums.ResponseMessage.*;
@Service
public class CarBookingService {
    private final CarBookingRepository carBookingRepository;
    private final CarService carService;
    private final AppUserService userService;

    public CarBookingService(CarBookingRepository carBookingRepository, CarService carService, AppUserService userService) {
        this.carBookingRepository = carBookingRepository;
        this.carService = carService;
        this.userService = userService;
    }

    public List<CarBookingResponse> getAllBookings(){
        return carBookingRepository
                .findAll()
                .stream()
                .map(carBooking -> new CarBookingResponse(
                carBooking.getId(),
                carBooking.getUser().getName(),
                carBooking.getCar().getRegNumber(),
                carBooking.getCar().getBrand(),
                carBooking.getStartDate(),
                carBooking.getEndDate(),
                carBooking.getCar().getRentalPricePerDay(),
                carBooking.getStatus(),
                carBooking.getBookedAt()))
                .toList();
    }
    public CarBookingResponse book(CarBookingRequest carBookingRequest) {
        Car car = carService.getAvailableCarById(carBookingRequest.carId());
        AppUser appUser = userService.getUserById(carBookingRequest.userId());

        CarBooking carBooking = new CarBooking(
                appUser,
                car,
                carBookingRequest.startDate(),
                carBookingRequest.endDate(),
                BookingStatus.ACTIVE);
        carBookingRepository.save(carBooking);

        carService.bookCarById(carBookingRequest.carId());
        return new CarBookingResponse(carBooking.getId(),
                appUser.getName(),
                car.getRegNumber(),
                car.getBrand(),
                carBooking.getStartDate(),
                carBooking.getEndDate(),
                car.getRentalPricePerDay(),
                carBooking.getStatus(),
                carBooking.getBookedAt());
    }

    public List<CarBooking> getUserBookedCarsByUserId(UUID userId) {
        AppUser appUser =  userService.getUserById(userId);
        if (appUser == null) {
            throw new ResourceNotFoundException(UNKNOWN_USER.getMessage());
        }
        return carBookingRepository.findAll()
                .stream()
                .filter(carBooking ->
                        carBooking.getUser().equals(appUser))
                .toList();
    }


    public CarBooking completeCarBookingById(UUID carBookingId) {
        Optional<CarBooking> bookingOptional = carBookingRepository.findCarBookingsByActiveStatusAndId(carBookingId);
        if (carBookingRepository.findCarBookingsByActiveStatus().isEmpty()) {
            throw new ResourceNotFoundException(NO_BOOKINGS.getMessage());

        }else if (bookingOptional.isEmpty()) {
            throw new ResourceNotFoundException(BOOKING_ID_NOT_FOUND.getMessage());

        }
        CarBooking carBooking = bookingOptional.get();
        carBooking.setStatus(BookingStatus.COMPLETED);
        carBookingRepository.save(carBooking);

        return carBooking;
    }
}
