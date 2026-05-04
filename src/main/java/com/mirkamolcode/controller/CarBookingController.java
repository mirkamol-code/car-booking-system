package com.mirkamolcode.controller;

import com.mirkamolcode.dto.CarBookingRequest;
import com.mirkamolcode.dto.CarBookingResponse;
import com.mirkamolcode.service.CarBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class CarBookingController {
    private final CarBookingService carBookingService;

    public CarBookingController(CarBookingService carBookingService) {
        this.carBookingService = carBookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings(){
        return ResponseEntity.ok(carBookingService.getAllBookings());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUsersBookings(@PathVariable UUID id){
        return ResponseEntity.ok(carBookingService.getUserBookedCarsByUserId(id));
    }

    @PostMapping
    public ResponseEntity<?> book(@RequestBody CarBookingRequest request){
        CarBookingResponse carBookingResponse = carBookingService.book(request);
        return ResponseEntity.ok(carBookingResponse);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> completeBooking(@PathVariable UUID bookingId){
            return ResponseEntity.ok(carBookingService.completeCarBookingById(bookingId));
    }

}
