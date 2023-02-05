package org.example.paternoster;

import org.example.GroupManager;
import org.example.states.Direction;

import java.util.ArrayList;

/**
 * Manager for elevators realising the Paternoster scheduling
 * Elevators stop on every floor
 */
public class NeverStopManager extends GroupManager<NeverStopElevator> {

    public NeverStopManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
        super(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors);
    }

    @Override
    protected void populateElevatorsArray(int numberOfElevators) {
        elevators = new ArrayList<>(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++)
            elevators.add(new NeverStopElevator(i, 0, (i % 2 == 0) ? Direction.UP : Direction.DOWN));
    }

    @Override
    protected NeverStopElevator getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        return null;
    }

    @Override
    protected void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void beforeStep() {
        for (NeverStopElevator e : getElevators()) {
            e.calculateDestination(numberOfPositiveFloors, numberOfNegativeFloors);
        }
    }

    @Override
    protected void afterStep() {
        for (NeverStopElevator e : getElevators()) {
            e.calculateDestination(numberOfPositiveFloors, numberOfNegativeFloors);
        }
    }
}
