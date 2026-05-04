package com.mirkamolcode.dto;

import com.mirkamolcode.model.enums.BookingStatus;
import com.mirkamolcode.model.enums.Brand;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CarBookingResponse(
        UUID id,
        String userName,
        String carRegNumber,
        Brand carBrand,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal price,
        BookingStatus status,
        Instant bookedAt
) {}
