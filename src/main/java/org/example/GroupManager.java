package org.example;

import org.example.utils.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class GroupManager<T extends Elevator> {

    protected final int numberOfElevators;
    protected final int numberOfPositiveFloors; // Num of floors above the ground floor.
    protected final int numberOfNegativeFloors; // Num of floors below the ground floor.
    protected ArrayList<T> elevators;


    public ArrayList<T> getElevators() {
        return elevators;
    }

    protected abstract void populateElevatorsArray(int numberOfElevators);

    protected abstract T getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void beforeStep();

    protected abstract void afterStep();

    public GroupManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
        if (numberOfElevators <= 0)
            throw new IllegalArgumentException("numberOfElevators (" + numberOfElevators + ") must be > 0");
        if (numberOfPositiveFloors < 0)
            throw new IllegalArgumentException("numberOfPositiveFloors (" + numberOfPositiveFloors + ") must be >= 0");
        if (numberOfNegativeFloors < 0)
            throw new IllegalArgumentException("numberOfNegativeFloors (" + numberOfNegativeFloors + ") must be >= 0");
        if (numberOfPositiveFloors + numberOfNegativeFloors == 0)
            throw new IllegalArgumentException("System must have at least one non-ground floor." +
                    " numberOfPositiveFloors and numberOfNegativeFloors cannot be equal to 0 simultaneously");
        this.numberOfElevators = numberOfElevators;
        this.numberOfPositiveFloors = numberOfPositiveFloors;
        this.numberOfNegativeFloors = numberOfNegativeFloors;
        populateElevatorsArray(numberOfElevators);
    }

    public List<Triplet<Integer, Integer, Integer>> status() {
        return elevators.stream().map(Elevator::getStatus).collect(Collectors.toList());
    }

    private void validatePickUpArguments(int callingFloor, int toFloor) {
        if (callingFloor > numberOfPositiveFloors)
            throw new IllegalArgumentException("callingFloor (" + callingFloor + ") is above the highest floor " +
                    "(numberOfPositiveFloors=" + numberOfPositiveFloors + ")");
        if (callingFloor < -numberOfNegativeFloors)
            throw new IllegalArgumentException("callingFloor (" + callingFloor + ") is below the lowest floor " +
                    "(numberOfNegativeFloors=" + numberOfNegativeFloors + ")");
        if (toFloor > numberOfPositiveFloors)
            throw new IllegalArgumentException("toFloor (" + toFloor + ") is above the highest floor " +
                    "(numberOfPositiveFloors=" + numberOfPositiveFloors + ")");
        if (toFloor < -numberOfNegativeFloors)
            throw new IllegalArgumentException("toFloor (" + toFloor + ") is below the lowest floor " +
                    "(numberOfNegativeFloors=" + numberOfNegativeFloors + ")");
    }

    public void pickUp(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        validatePickUpArguments(callingFloor, toFloor);
        // Add an inquiry to the nearest Elevator, which has an appropriate state
        Elevator selectedElevator = getSelectedElevator(callingFloor, upButtonPressed, toFloor);
        if (selectedElevator == null)
            doIfElevatorIsNull(callingFloor, upButtonPressed, toFloor);
        else selectedElevator.pickUp(callingFloor, upButtonPressed, toFloor);

    }

    public void pickUp(int elevatorId, int toFloor) {
        Elevator elevator = elevators.stream().filter(e -> e.getId() == elevatorId).findFirst().orElse(null);
        if (elevator == null) throw new NoSuchElementException("There is no elevator with id=" + elevatorId);
        elevator.pickUp(toFloor);
    }


    public void step() {
        beforeStep();
        for (Elevator e : getElevators()) e.step();
        afterStep();
    }

}
