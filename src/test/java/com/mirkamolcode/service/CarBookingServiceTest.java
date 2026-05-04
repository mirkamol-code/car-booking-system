package com.mirkamolcode.service;

import com.mirkamolcode.dto.CarBookingRequest;
import com.mirkamolcode.dto.CarBookingResponse;
import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.AppUser;
import com.mirkamolcode.model.Car;
import com.mirkamolcode.model.CarBooking;
import com.mirkamolcode.model.enums.BookingStatus;
import com.mirkamolcode.model.enums.Brand;
import com.mirkamolcode.repository.CarBookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.mirkamolcode.model.enums.BookingStatus.*;
import static com.mirkamolcode.model.enums.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CarBookingServiceTest {

    @Mock
    private AppUserService userService;

    @Mock
    private CarService carService;

    @Mock
    private CarBookingRepository carBookingRepository;

    @InjectMocks
    private CarBookingService underTest;

    @Test
    void shouldGetAllBookings() {
        // given
        AppUser user = new AppUser("James");
        Car car = new Car("2222", BigDecimal.valueOf(20.0), Brand.AUDI, false);
        CarBooking carBooking = new CarBooking(user, car, LocalDate.now(), LocalDate.now().plusDays(2), ACTIVE);
        List<CarBooking> expected = List.of(carBooking);
        given(carBookingRepository.findAll()).willReturn(expected);
        // when
        List<CarBookingResponse> actual = underTest.getAllBookings();
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getFirst().id()).isEqualTo(expected.getFirst().getId());
    }

    @Test
    void shouldReturnEmptyListCarBookingListIsEmpty() {
        // given
        given(carBookingRepository.findAll()).willReturn(new ArrayList<>());
        // then
        assertThat(underTest.getAllBookings()).isEmpty();
    }

    @Test
    void shouldBook() {
        // given
        AppUser user = new AppUser(UUID.randomUUID(), "James");
        Car car = new Car("2222", BigDecimal.valueOf(20.0), Brand.AUDI, false);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        CarBooking expected = new CarBooking(
                user,
                car,
                startDate,
                endDate,
                ACTIVE
        );
        CarBookingRequest carBookingRequest = new CarBookingRequest(
                user.getId(),
                car.getId(),
                startDate,
                endDate
        );
        given(carService.getAvailableCarById(car.getId())).willReturn(car);
        given(userService.getUserById(user.getId())).willReturn(user);
        given(carBookingRepository.save(any())).willReturn(expected);
        // when
        CarBookingResponse actual = underTest.book(carBookingRequest);
        // then
        then(carService).should().getAvailableCarById(car.getId());
        then(userService).should().getUserById(user.getId());
        then(carBookingRepository).should().save(any());

        assertThat(actual.carRegNumber()).isEqualTo(car.getRegNumber());
        assertThat(actual.userName()).isEqualTo(user.getName());
        assertThat(actual.startDate()).isEqualTo(startDate);
        assertThat(actual.endDate()).isEqualTo(endDate);
        assertThat(actual.status()).isEqualTo(ACTIVE);
        assertThat(actual.price()).isEqualTo(car.getRentalPricePerDay());

        then(carService).should().bookCarById(car.getId());
        then(carService).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoMoreInteractions();
        then(carBookingRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowWhenUserNotFoundToBook() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        Car car = new Car(
                "1111",
                BigDecimal.valueOf(20.0),
                Brand.AUDI,
                true
        );

        CarBookingRequest carBookingRequest = new CarBookingRequest(
                UUID.randomUUID(),
                car.getId(),
                startDate,
                endDate
        );

        given(carService.getAvailableCarById(any())).willReturn(car);
        given(userService.getUserById(any()))
                .willThrow(new ResourceNotFoundException(UNKNOWN_USER.getMessage()));
        // when
        assertThatThrownBy(() -> underTest.book(carBookingRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(UNKNOWN_USER.getMessage());
        then(carService).should().getAvailableCarById(car.getId());
        then(carBookingRepository).shouldHaveNoInteractions();
        then(carService).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowWhenCarNotFoundToBook() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        AppUser jamila = new AppUser("Jamila");

        CarBookingRequest carBookingRequest = new CarBookingRequest(
                jamila.getId(),
                UUID.randomUUID(),
                startDate,
                endDate
        );

        given(carService.getAvailableCarById(any()))
                .willThrow(new ResourceNotFoundException(CAR_NOT_FOUND.getMessage()));

        // when
        assertThatThrownBy(() -> underTest.book(carBookingRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(CAR_NOT_FOUND.getMessage());
        then(carBookingRepository).shouldHaveNoInteractions();
        then(carService).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoMoreInteractions();
    }

    @Test
    void getUserBookedCarsByUserId() {
        // given
        AppUser user = new AppUser(UUID.randomUUID(), "John");
        Car bookedCar1 = new Car("1111", BigDecimal.valueOf(20.0), Brand.AUDI, false);
        Car bookedCar2 = new Car("2222", BigDecimal.valueOf(40.0), Brand.MERCEDES, true);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        CarBooking carBooking = new CarBooking(user, bookedCar1, startDate, endDate, ACTIVE);
        CarBooking carBooking2 = new CarBooking(user, bookedCar2, startDate, endDate, ACTIVE);
        List<CarBooking> expected = List.of(carBooking, carBooking2);
        given(userService.getUserById(user.getId())).willReturn(user);
        given(underTest.getUserBookedCarsByUserId(user.getId())).willReturn(expected);
        // when
        List<CarBooking> actual = underTest.getUserBookedCarsByUserId(user.getId());
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenUserNotFoundToGetUserBookedCars() {
        // given
        given(userService.getUserById(any())).willReturn(null);
        // then
        assertThatThrownBy(() -> underTest.getUserBookedCarsByUserId(any()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(UNKNOWN_USER.getMessage());
    }

    @Test
    void shouldCompleteCarBooking() {
        // given
        AppUser user = new AppUser(UUID.randomUUID(), "John");
        Car bookedCar1 = new Car("1111", BigDecimal.valueOf(20.0), Brand.AUDI, false);
        Car bookedCar2 = new Car("2222", BigDecimal.valueOf(40), Brand.MERCEDES, true);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        CarBooking carBooking = new CarBooking(user, bookedCar1, startDate, endDate, ACTIVE);
        CarBooking carBooking2 = new CarBooking(user, bookedCar2, startDate, endDate, CANCELLED);
        List<CarBooking> carBookings = List.of(carBooking, carBooking2);
        given(carBookingRepository.findCarBookingsByActiveStatus()).willReturn(carBookings);
        given(carBookingRepository.findCarBookingsByActiveStatusAndId(carBooking.getId())).willReturn(Optional.of(carBooking));
        // when
        underTest.completeCarBookingById(carBooking.getId());
        // then
        then(carBookingRepository).should().save(carBooking);
        assertThat(carBooking.getStatus()).isEqualTo(COMPLETED);
    }

    @Test
    void shouldThrowWhenBookingListIsEmptyToCompleteCarBooking() {
        // given
       var bookingId = UUID.randomUUID();
        given(carBookingRepository.findCarBookingsByActiveStatus()).willReturn(new ArrayList<>());
        // then
        assertThatThrownBy(() -> underTest.completeCarBookingById(bookingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(NO_BOOKINGS.getMessage());
        then(carBookingRepository).should().findCarBookingsByActiveStatusAndId(bookingId);
        then(carBookingRepository).should().findCarBookingsByActiveStatus();
        then(carBookingRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowWhenBookingIdNotFoundToCompleteCarBooking() {
        // given
        AppUser user = new AppUser(UUID.randomUUID(), "John");
        Car bookedCar1 = new Car("1111", BigDecimal.valueOf(20.0), Brand.AUDI, false);
        Car bookedCar2 = new Car("2222", BigDecimal.valueOf(40), Brand.MERCEDES, true);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        CarBooking carBooking = new CarBooking(user, bookedCar1, startDate, endDate, ACTIVE);
        CarBooking carBooking2 = new CarBooking(user, bookedCar2, startDate, endDate, CANCELLED);
        List<CarBooking> carBookings = List.of(carBooking, carBooking2);

        given(carBookingRepository.findCarBookingsByActiveStatus()).willReturn(carBookings);
        given(carBookingRepository.findCarBookingsByActiveStatusAndId(any())).willReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.completeCarBookingById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(BOOKING_ID_NOT_FOUND.getMessage());
        then(carBookingRepository).shouldHaveNoMoreInteractions();
    }
}