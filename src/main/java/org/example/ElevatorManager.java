package org.example;

import org.example.states.Direction;
import org.example.utils.Triplet;

import java.util.*;
import java.util.stream.Collectors;

public class ElevatorManager {

    private final int numberOfElevators;
    private final int numberOfPositiveFloors; // Num of floors above the ground floor.
    private final int numberOfNegativeFloors; // Num of floors below the ground floor.
    private final Elevator[] elevators;
    private final ArrayList<Triplet<Integer, Boolean, Integer>> upCache = new ArrayList<>();
    private final ArrayList<Triplet<Integer, Boolean, Integer>> downCache = new ArrayList<>();


    ElevatorManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
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
        this.elevators = new Elevator[numberOfElevators];
        for (int i = 0; i < numberOfElevators; i++) this.elevators[i] = new Elevator(i, 0);
    }

    public List<Triplet<Integer, Integer, Integer>> status() {
        return Arrays.stream(elevators).map(Elevator::getStatus).collect(Collectors.toList());
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
        Elevator nearest = Arrays.stream(elevators)
                // don't consider elevators which are "running away" from the callingFloor
                .filter(e -> !(e.getCurrentFloor() > callingFloor && e.getDirection() == Direction.UP))
                .filter(e -> !(e.getCurrentFloor() < callingFloor && e.getDirection() == Direction.DOWN))
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - callingFloor)))
                .orElse(null);
        if (nearest == null) {
            //Save inquiry to the cache
            ArrayList<Triplet<Integer, Boolean, Integer>> tmp = upButtonPressed ? upCache : downCache;
            tmp.add(new Triplet<>(callingFloor, upButtonPressed, toFloor));
        } else {
            nearest.pickUp(callingFloor, upButtonPressed, toFloor);
        }
    }

    public void pickUp(int elevatorId, int toFloor) {
        Elevator elevator = Arrays.stream(elevators).filter(e -> e.getId() == elevatorId).findFirst().orElse(null);
        if (elevator == null) throw new NoSuchElementException("There is no elevator with id=" + elevatorId);
        elevator.pickUp(toFloor);
    }

    private void flushCache(ArrayList<Triplet<Integer, Boolean, Integer>> cache) {
        if (cache != upCache && cache != downCache)
            throw new IllegalArgumentException("cache must be either upCache or downCache");
        Arrays.stream(elevators)
                .filter(e -> e.getDirection() == Direction.NONE)
                .findFirst()
                .ifPresent(e -> {
                    e.bulkPickUp(cache);
                    cache.clear();
                });
    }

    public void step() {
        for (Elevator e : elevators) e.step();
        if (!upCache.isEmpty()) flushCache(upCache);
        if (!downCache.isEmpty()) flushCache(downCache);
    }

    @Override
    public String toString() {
        StringBuilder elevatorsString = new StringBuilder();
        Arrays.stream(elevators).forEach(e -> elevatorsString.append(e.toString()));
        return "ElevatorManager{\n" +
                "numberOfElevators=" + numberOfElevators +
                "\nnumberOfPositiveFloors=" + numberOfPositiveFloors +
                "\nnumberOfNegativeFloors=" + numberOfNegativeFloors +
                "\nelevators=\n" + elevatorsString +
                "\nupCache=" + upCache +
                "\ndownCache=" + downCache +
                '}';
    }
}
