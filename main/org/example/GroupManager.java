package org.example;

import org.example.utils.ElevatorStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Class responsible for managing a group of elevators
 *
 * @param <E>
 */
public abstract class GroupManager<E extends Elevator> {

    protected final int numberOfElevators;
    protected final int numberOfPositiveFloors; // Num of floors above the ground floor.
    protected final int numberOfNegativeFloors; // Num of floors below the ground floor.
    protected List<E> elevators;

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

    public List<ElevatorStatus> status() {
        return elevators.stream().map(Elevator::getStatus).collect(Collectors.toList());
    }

    public void pickUp(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        validatePickUpArguments(callingFloor, (toFloor == null) ? callingFloor : toFloor);
        // Add an inquiry to the nearest Elevator, which has an appropriate state
        Elevator selectedElevator = getSelectedElevator(callingFloor, upButtonPressed, toFloor);
        if (selectedElevator == null)
            doIfElevatorIsNull(callingFloor, upButtonPressed, toFloor);
        else selectedElevator.pickUp(callingFloor, upButtonPressed, toFloor);
    }

    public void pickUp(int elevatorId, int toFloor) {
        validateToFloor(toFloor);
        Elevator elevator = elevators.stream().filter(e -> e.getId() == elevatorId).findFirst().orElse(null);
        if (elevator == null) throw new NoSuchElementException("There is no elevator with id=" + elevatorId);
        elevator.pickUp(toFloor);
    }

    public void step() {
        beforeStep();
        for (Elevator e : getElevators()) e.step();
        afterStep();
    }

    @Override
    public String toString() {
        StringBuilder elevatorsString = new StringBuilder();
        elevators.forEach(e -> elevatorsString.append("\t\t").append(e.toString()));
        return this.getClass().getSimpleName() + "{" +
                "\n\tnumberOfElevators=" + numberOfElevators +
                "\n\tnumberOfPositiveFloors=" + numberOfPositiveFloors +
                "\n\tnumberOfNegativeFloors=" + numberOfNegativeFloors +
                "\n\televators=\n" + elevatorsString +
                '}';
    }

    public List<E> getElevators() {
        return elevators;
    }

    protected abstract void populateElevatorsArray(int numberOfElevators);

    protected abstract E getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void beforeStep();

    protected abstract void afterStep();

    private void validatePickUpArguments(int callingFloor, int toFloor) {
        validateCallingFloor(callingFloor);
        validateToFloor(toFloor);
    }

    private void validateCallingFloor(int callingFloor) {
        if (callingFloor > numberOfPositiveFloors)
            throw new IllegalArgumentException("callingFloor (" + callingFloor + ") is above the highest floor " +
                    "(numberOfPositiveFloors=" + numberOfPositiveFloors + ")");
        if (callingFloor < -numberOfNegativeFloors)
            throw new IllegalArgumentException("callingFloor (" + callingFloor + ") is below the lowest floor " +
                    "(numberOfNegativeFloors=" + numberOfNegativeFloors + ")");
    }

    private void validateToFloor(int toFloor) {
        if (toFloor > numberOfPositiveFloors)
            throw new IllegalArgumentException("toFloor (" + toFloor + ") is above the highest floor " +
                    "(numberOfPositiveFloors=" + numberOfPositiveFloors + ")");
        if (toFloor < -numberOfNegativeFloors)
            throw new IllegalArgumentException("toFloor (" + toFloor + ") is below the lowest floor " +
                    "(numberOfNegativeFloors=" + numberOfNegativeFloors + ")");
    }

}
