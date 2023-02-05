package org.example.utils;

public class PickUpOrder {
    private final int callingFloor;
    private final boolean upButtonPressed;
    private final int toFloor;

    public int getCallingFloor() {
        return callingFloor;
    }

    public boolean isUpButtonPressed() {
        return upButtonPressed;
    }

    public int getToFloor() {
        return toFloor;
    }

    public PickUpOrder(int callingFloor, boolean upButtonPressed, int toFloor) {
        this.callingFloor = callingFloor;
        this.upButtonPressed = upButtonPressed;
        this.toFloor = toFloor;
    }
}
