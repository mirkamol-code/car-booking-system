package com.mirkamolcode.model.enums;

public enum ResponseMessage {
    INVALID_OPTION(" not a valid option ❌"),
    NO_BOOKINGS("no bookings available 😕"),
    SELECTION_OF_USER_ID("➡️ select user id"),
    SELECTION_OF_CAR_REG_NUMBER("➡️ select car reg number"),
    SELECTION_OF_BOOKING_ID("➡️ select booking id number"),
    BOOKED_CAR("🎉 Successfully booked car with reg number "),
    BOOKING_REF("Booking ref: "),
    X_USER(" ❌ user "), NOT_BOOKED(" has no car booked"),
    FOR_USER(" for user "),
    NO_CARS("❌ No cars available for renting"),
    NO_ELECTRIC_CARS("❌ No electric cars available for renting"),
    UNKNOWN_USER("❌ User Not Found"),
    BOOKING_ID_NOT_FOUND(" ❌ not found "),
    CAR_NOT_FOUND(" ❌ Car Not Found "),
    UNABLE_TO_BOOK(" ❌ Unable to book"),
    NO_USERS("❌ There is no users"),
    LIST_OF_USERS("List of users"),
    FOUND_USER("Found user");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
