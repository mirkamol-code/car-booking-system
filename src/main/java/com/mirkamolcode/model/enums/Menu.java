package com.mirkamolcode.model.enums;

public enum Menu {
    CANCEL_BOOKING("0️⃣ - Cancel the booking"),
    BOOK_CAR("1️⃣ - Book Car"),
    VIEW_ALL_USER_BOOKED_CARS("2️⃣ - View All User Booked Cars"),
    VIEW_ALL_BOOKINGS("3️⃣ - View All Bookings"),
    VIEW_AVAILABLE_CARS("4️⃣ - View Available Cars"),
    VIEW_AVAILABLE_ELECTRIC_CARS("5️⃣ - View Available Electric Cars"),
    VIEW_ALL_USERS("6️⃣ - View all users"),
    EXIT("7️⃣ - Exit");

    private final String message;

    Menu(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
