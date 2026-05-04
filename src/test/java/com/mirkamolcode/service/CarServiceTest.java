package com.mirkamolcode.service;

import com.mirkamolcode.dto.CarResponse;
import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.Car;
import com.mirkamolcode.model.enums.Brand;
import com.mirkamolcode.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static com.mirkamolcode.model.enums.ResponseMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks CarService underTest;


    @Test
    void shouldGetAllCars(){
        // given
        List<Car> expected = List.of(
                new Car("111", BigDecimal.valueOf(20), Brand.BMW, false),
                new Car("222", BigDecimal.valueOf(10), Brand.MERCEDES, true)
        );
        given(carRepository.findAll()).willReturn(expected);

        // when
        List<CarResponse> actual = underTest.getAllCars();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual.getFirst().id()).isEqualTo(expected.getFirst().getId());
    }
    @Test
    void shouldGetAllAvailableCars(){
        // given
        List<Car> expected = List.of(
                new Car("111", BigDecimal.valueOf(20), Brand.BMW, false),
                new Car("222", BigDecimal.valueOf(10), Brand.MERCEDES, true)
        );
        given(carRepository.findByIsAvailableTrue()).willReturn(expected);

        // when
        List<CarResponse> actual = underTest.getAllAvailableCars();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual.getFirst().id()).isEqualTo(expected.getFirst().getId());
    }

    @Test
    void shouldReturnEmptyListWhenCarListIsEmpty() {
        // given
        given(carRepository.findAll()).willReturn(new ArrayList<>());

        // then
        assertThat(underTest.getAllCars()).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenAvailableCarListIsEmpty() {
        // given
        given(carRepository.findByIsAvailableTrue()).willReturn(new ArrayList<>());

        // then
        assertThat(underTest.getAllAvailableCars()).isEmpty();
    }


    @Test
    void shouldGetElectricCars() {
        // given
        List<Car> expected = new ArrayList<>(Arrays.asList(
                new Car("2222", BigDecimal.valueOf(20.0), Brand.MERCEDES, true),
                new Car("3333", BigDecimal.valueOf(15.0), Brand.MERCEDES, true)
        ));
        given(carRepository.findByIsElectricTrue()).willReturn(expected);
        // when
        List<CarResponse> actual = underTest.getElectricCars();
        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual.getFirst().isElectric()).isEqualTo(expected.getFirst().isElectric());
        assertThat(actual.getLast().isElectric()).isEqualTo(expected.getLast().isElectric());
    }

    @Test
    void shouldReturnEmptyListWhenElectricCarListIsEmpty() {
        // given
        given(carRepository.findByIsElectricTrue()).willReturn(new ArrayList<>());

        // then
        assertThat(underTest.getElectricCars()).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenAvailableElectricCarListIsEmpty() {
        // given
        given(carRepository.findByAvailableElectricCars()).willReturn(new ArrayList<>());

        // then
        assertThat(underTest.getAvailableElectricCars()).isEmpty();
    }

    @Test
    void shouldGetCarById() {
        // given
        Car expected = new Car("2222", BigDecimal.valueOf(10.0), Brand.MERCEDES, true);
        given(carRepository.findById(expected.getId())).willReturn(Optional.of(expected));
        // when
        Car actual = underTest.getCarById(expected.getId());
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetAvailableCarById() {
        // given
        Car expected = new Car("2222", BigDecimal.valueOf(10.0), Brand.MERCEDES, true);
        given(carRepository.findAvailableCarById(expected.getId())).willReturn(Optional.of(expected));
        // when
        Car actual = underTest.getAvailableCarById(expected.getId());
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenCarNotFound() {
        // given
        given(carRepository.findById(any())).willReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> underTest.getCarById(any()))
                .hasMessageContaining(CAR_NOT_FOUND.getMessage())
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowWhenAvailableCarNotFound() {
        // given
        given(carRepository.findAvailableCarById(any())).willReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> underTest.getAvailableCarById(any()))
                .hasMessageContaining(CAR_NOT_FOUND.getMessage())
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldBookCar() {
        // given
        Optional<Car> expected = Optional.of(new Car("2222", BigDecimal.valueOf(15.0), Brand.MERCEDES, true));
        UUID carId = expected.get().getId();
        given(carRepository.findAvailableCarById(carId)).willReturn(expected);
        // when
        Car car = underTest.bookCarById(carId);
        // then
        then(carRepository).should().save(expected.get());
        then(carRepository).shouldHaveNoMoreInteractions();
        assertThat(car.isAvailable()).isFalse();
    }

    @Test
    void shouldThrowWhenRegNumberIsNotFoundToDeleteCar() {
        // given
        given(carRepository.findAvailableCarById(any())).willReturn(Optional.empty());
        // then
        then(carRepository).shouldHaveNoInteractions();
        assertThatThrownBy(() -> underTest.bookCarById(any()))
                .hasMessageContaining(CAR_NOT_FOUND.getMessage())
                .isInstanceOf(ResourceNotFoundException.class);
    }

}