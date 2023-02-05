package org.example.utils;

import java.util.OptionalInt;

public class ElevatorStatus {
    private final int id;
    private final int currentFloor;
    private final OptionalInt destinationFloor;

    public ElevatorStatus(int id, int currentFloor, OptionalInt destinationFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    @Override
    public String toString() {
        return "<" + id + ',' + currentFloor + ',' + (destinationFloor.isPresent() ? destinationFloor.getAsInt() : "-") + '>';
    }
}
