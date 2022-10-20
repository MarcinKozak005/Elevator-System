package org.example.elevatormanager;

import org.example.elevator.Elevator;

import java.util.ArrayList;

public abstract class ElevatorManager<T extends Elevator> {
    protected ArrayList<T> elevators;

    public ArrayList<T> getElevators() {
        return elevators;
    }

    protected abstract void populateElevatorsArray(int numberOfElevators);
    protected abstract T getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor);
    protected abstract void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void beforeStep();

    protected abstract void afterStep();
}
