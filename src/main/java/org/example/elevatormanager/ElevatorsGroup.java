package org.example.elevatormanager;

import org.example.elevator.Elevator;
import org.example.utils.Triplet;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ElevatorsGroup<T extends Elevator> {

    private final int numberOfElevators;
    private final int numberOfPositiveFloors; // Num of floors above the ground floor.
    private final int numberOfNegativeFloors; // Num of floors below the ground floor.
    GroupManager<T> groupManager;

    public ElevatorsGroup(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors, GroupManager<T> groupManager) {
        if (groupManager == null)
            throw new IllegalArgumentException("managerSchedulingStrategy cannot be null");
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
        this.groupManager = groupManager;
        groupManager.populateElevatorsArray(numberOfElevators);
    }

    public List<Triplet<Integer, Integer, Integer>> status() {
        return groupManager.getElevators().stream().map(Elevator::getStatus).collect(Collectors.toList());
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
        Elevator selectedElevator = groupManager.getSelectedElevator(callingFloor, upButtonPressed, toFloor);
        if (selectedElevator == null)
            groupManager.doIfElevatorIsNull(callingFloor, upButtonPressed, toFloor);
        else selectedElevator.pickUp(callingFloor, upButtonPressed, toFloor);

    }

    public void pickUp(int elevatorId, int toFloor) {
        Elevator elevator = groupManager.getElevators().stream().filter(e -> e.getId() == elevatorId).findFirst().orElse(null);
        if (elevator == null) throw new NoSuchElementException("There is no elevator with id=" + elevatorId);
        elevator.pickUp(toFloor);
    }


    public void step() {
        groupManager.beforeStep();
        for (Elevator e : groupManager.getElevators()) e.step();
        groupManager.afterStep();
    }

    @Override
    public String toString() {
        StringBuilder elevatorsString = new StringBuilder();
        groupManager.getElevators().forEach(e -> elevatorsString.append(e.toString()));
        return this.getClass().getSimpleName()+"{\n" +
                "numberOfElevators=" + numberOfElevators +
                "\nnumberOfPositiveFloors=" + numberOfPositiveFloors +
                "\nnumberOfNegativeFloors=" + numberOfNegativeFloors +
                "\ngroupManager=" + groupManager +
                "\nelevators=\n" + elevatorsString +
                '}';
    }
}
