package org.example.fcfs;

import org.example.GroupManager;

import java.util.ArrayList;
import java.util.Comparator;

public class FCFSManager extends GroupManager<FCFSElevator> {
    public FCFSManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
        super(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors);
    }

    @Override
    protected void populateElevatorsArray(int numberOfElevators) {
        elevators = new ArrayList<>(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++) elevators.add(new FCFSElevator(i, 0));
    }

    @Override
    protected FCFSElevator getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        return elevators.stream().min(Comparator.comparingInt(FCFSElevator::getQueueSize)).orElse(null);
    }

    @Override
    protected void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void beforeStep() {

    }

    @Override
    protected void afterStep() {

    }
}
