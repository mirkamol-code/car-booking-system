# 🚗 Car Booking Backend (Spring Boot)

A Spring Boot REST API for managing car bookings.  
The application allows users to view available cars, create bookings, track reservations, and complete active bookings.

---

## 🔄 Project Evolution

This project started as a command-line (CLI) application written in core Java.  
It has been refactored into a Spring Boot REST API with proper layered architecture.

- 🔹 CLI Version: https://github.com/USERNAME/car-booking-cli  
- 🔹 Spring Boot Version: Current Repository  

### Key Improvements
- Introduced RESTful API architecture  
- Added Spring Data JPA for persistence  
- Implemented layered design (Controller–Service–Repository)  
- Added database integration  
- Improved scalability and maintainability  

---

## 🛠 Tech Stack

- Java (JDK 25)
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate / Jakarta Persistence
- H2 In-Memory Database
- Maven
- JUnit 5
- Mockito
- AssertJ

---

## 📌 Features

- View available cars
- Filter electric cars
- Book a car
- View bookings by user
- Complete bookings (mark as COMPLETED)
- Automatic car availability management
- Global exception handling
- In-memory database (H2)
- Unit testing for service layer

---

## 📂 Project Structure
src/main/java/com/mirkamolcode/
├── controller # REST Controllers (API layer)
├── service # Business logic layer
├── repository # Data access layer
├── model # Entity classes
├── dto # Request/Response objects
├── exception # Global exception handling
src/test/java/com/mirkamolcode/
├── service # Unit tests for service layer

---

## 📐 Domain Model

### AppUser
Represents a system user.

### Car
Represents a rentable vehicle with availability status.

### CarBooking
Represents a reservation between a user and a car.

Booking status:
- ACTIVE
- COMPLETED
- CANCELLED

---

## 🚀 Running the Project

### Requirements
- JDK 25
- Maven

### Run application
```bash
./mvnw spring-boot:run

### 🧪 Running Tests
Run all tests:
./mvnw test
Test Coverage
Service layer unit tests
Business logic validation
Booking flow verification

🗄 Database
Uses H2 in-memory database.
H2 Console:
http://localhost:8080/api/v1/h2-console
