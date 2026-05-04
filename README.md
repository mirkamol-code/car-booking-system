# Car Booking CLI Project

[![Java](https://img.shields.io/badge/Java-25-blue?logo=java&logoColor=white)](https://www.java.com/)
[![GitHub](https://img.shields.io/badge/GitHub-Project-black?logo=github)](https://github.com/)

---

## Table of Contents

- [Overview](#overview)  
- [Project Goals](#project-goals)  
- [Technologies Used](#technologies-used)  
- [Project Architecture](#project-architecture)  
  - [Domain / Model Layer](#1-domain--model-layer)  
  - [DAO Layer](#2-dao-data-access-object-layer)  
  - [Service Layer](#3-service-layer)  
  - [Main / CLI Layer](#4-main--cli-layer)  
- [Features Implemented](#features-implemented)  
- [Key Challenges](#key-challenges)  
- [Enhancements Completed](#enhancements-completed)  
- [Future Improvements](#future-improvements)  
- [Learning Outcomes](#learning-outcomes)  
- [Demo](#demo)  
- [Author](#author)  

---

## Overview

The **Car Booking CLI Project** is a console-based Java application that simulates a simple car rental and booking system. The project focuses on applying core Java concepts such as object-oriented programming, layered architecture, and data management using `ArrayList` along with file-based persistence for saving and loading data.

This project was developed as a learning-oriented system to strengthen practical skills in Java, Git, and clean project structuring.

---

## Project Goals

- Practice core Java fundamentals through a real-world use case  
- Understand and apply layered architecture (Domain, DAO, Service)  
- Implement business logic without relying on external databases  
- Build a fully interactive CLI application using `Scanner`  
- Handle common challenges related to dynamic data structures and data persistence  

---

## Technologies Used

- **Java (Core Java)**  
- **Command Line Interface (CLI)**  
- **Git & GitHub**  
- **File-based persistence** for saving and loading application data  
- No external frameworks or databases  

---

## Project Architecture

The project follows a layered architecture pattern:

### 1. Domain / Model Layer

Contains the core entities of the system, such as:

- User  
- Car  
- ElectricCar  
- Booking  

These classes define the structure and attributes of system objects.

---

### 2. DAO (Data Access Object) Layer

Responsible for managing data storage and retrieval. The project uses `ArrayList` for in-memory data storage and implements **file-based persistence** to save and load data across application runs.

Responsibilities include:

- Storing appUsers, cars, and bookings  
- Searching by ID or registration number  
- Adding and removing elements dynamically  

---

### 3. Service Layer

Contains business logic and acts as a bridge between the DAO layer and the CLI.

Responsibilities include:

- Booking a car  
- Validating appUser and car availability  
- Filtering electric cars  
- Coordinating operations across multiple DAOs  

---

### 4. Main / CLI Layer

Handles appUser interaction via the command line.

Responsibilities include:

- Displaying menus  
- Reading appUser input using `Scanner`  
- Calling appropriate service methods  
- Running the application inside a loop  

---

## Features Implemented

- View all appUsers  
- View all available cars  
- View all bookings  
- Get a car by registration number  
- Get a appUser by ID  
- View electric cars only  
- Book a car  

---

## Key Challenges

### Data Management Without a Database

Instead of using a traditional database, the project manages data using `ArrayList` and file-based persistence. Challenges included:

- Maintaining data consistency  
- Dynamically adding or removing elements  
- Ensuring correct saving and loading of application data  

These challenges were addressed by:

- Using `ArrayList` for flexible in-memory storage  
- Implementing file-based persistence for reading and writing data  
- Structuring DAO classes to isolate data management responsibilities  

---

## Enhancements Completed

- Automatically remove booked cars from the available car list after successful booking  
- Replaced arrays with `ArrayList` for more flexible data handling  
- Added file-based persistence to save and load application data  
- Implemented exception handling for invalid appUser input  

---

## Future Improvements

- Add unit tests for service and DAO layers  
- Improve CLI UX with better menus and input validation  

---

## Learning Outcomes

- Stronger understanding of Java OOP principles  
- Hands-on experience with layered architecture  
- Improved problem-solving skills with dynamic data structures  
- Practical experience with CLI-based application flow and file I/O  

---

## Demo

<img width="778" height="306" alt="Screenshot 2026-01-29 at 11 28 52 PM" src="https://github.com/appUser-attachments/assets/b9f6f00d-5bf3-4b3f-bd87-d96138794e56" />


---

## Author

Developed as a learning project by **Mirkamol**.
