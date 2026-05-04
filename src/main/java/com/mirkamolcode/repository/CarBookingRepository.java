package com.mirkamolcode.repository;

import com.mirkamolcode.model.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarBookingRepository extends JpaRepository<CarBooking, UUID> {
    @Query("SELECT cb FROM CarBooking cb WHERE cb.status = BookingStatus.ACTIVE")
    List<CarBooking> findCarBookingsByActiveStatus();

    @Query("SELECT cb FROM CarBooking cb WHERE cb.status = BookingStatus.ACTIVE AND cb.id = ?1")
    Optional<CarBooking> findCarBookingsByActiveStatusAndId(UUID id);
}
