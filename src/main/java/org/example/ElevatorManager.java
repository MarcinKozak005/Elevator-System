package org.example;

import org.example.utils.Triplet;
import org.example.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ElevatorManager {

    private final int numberOfElevators;
    private final int numberOfPositiveFloors; // Num of floors above the ground level. Including ground level (0)
    private final int numberOfNegativeFloors; // Num of floors below the ground level.
    private final Elevator[] elevators;
    private final ArrayList<Tuple<Integer, Integer>> upCache = new ArrayList<>();
    private final ArrayList<Tuple<Integer, Integer>> downCache = new ArrayList<>();


    ElevatorManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
        this.numberOfElevators = numberOfElevators;
        this.numberOfPositiveFloors = numberOfPositiveFloors;
        this.numberOfNegativeFloors = numberOfNegativeFloors;
        this.elevators = new Elevator[numberOfElevators];
        for (int i = 0; i < numberOfElevators; i++) this.elevators[i] = new Elevator(i, 0);
    }

    public List<Triplet<Integer, Integer, Integer>> status() {
        return Arrays.stream(elevators).map(Elevator::getStatus).collect(Collectors.toList());
    }

    public void pickUp(int callingFloor, boolean upButtonPressed, int toFloor) {
        // Add a inquiry to the nearest Elevator, which has an appropriate state
        Elevator nearest = Arrays.stream(elevators)
                // don't consider elevators which are "running away" from the callingFloor
                .filter(e -> !(e.getCurrentFloor() > callingFloor && e.getNextAction() == Action.UP))
                .filter(e -> !(e.getCurrentFloor() < callingFloor && e.getNextAction() == Action.DOWN))
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - callingFloor)))
                .orElse(null);
        if (nearest == null) {
            //Save inquiry to the cache
            ArrayList<Tuple<Integer, Integer>> tmp = upButtonPressed ? upCache : downCache;
            tmp.add(new Tuple<>(callingFloor, toFloor));
        } else {
            nearest.pickUp(callingFloor, toFloor);
        }
    }

    private void flushCache(ArrayList<Tuple<Integer, Integer>> cache) {
        if (cache != upCache && cache != downCache) return;
        Arrays.stream(elevators)
                .filter(e -> e.getNextAction() == Action.IDLE)
                .findFirst()
                .ifPresent(e -> {
                    e.bulkPickUp(cache);
                    cache.clear();
                });
    }

    public void step() {
        if (!upCache.isEmpty()) flushCache(upCache);
        if (!downCache.isEmpty()) flushCache(downCache);
        for (Elevator e : elevators) e.step();
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
