package org.example;

import org.example.utils.Triplet;
import org.example.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ElevatorManager {

    int numberOfElevators;
    int numberOfPositiveFloors; // Num of floors above the ground level. Including ground level (0)
    int numberOfNegativeFloors; // Num of floors below the ground level.
    Elevator[] elevators;
    ArrayList<Tuple<Integer, Integer>> upCache = new ArrayList<>();
    ArrayList<Tuple<Integer, Integer>> downCache = new ArrayList<>();


    ElevatorManager(int numberOfElevators, int numberOfPositiveFloors, int numberOfNegativeFloors) {
        this.numberOfElevators = numberOfElevators;
        this.numberOfPositiveFloors = numberOfPositiveFloors;
        this.numberOfNegativeFloors = numberOfNegativeFloors;
        this.elevators = new Elevator[numberOfElevators];
        for (int i = 0; i < numberOfElevators; i++) this.elevators[i] = new Elevator(i, 0, 0);
    }

    public List<Triplet<Integer, Integer, Integer>> status() {
        return Arrays.stream(elevators).map(Elevator::getStatus).collect(Collectors.toList());
    }

    public void pickUp(int callingFloor, boolean up, int toFloor) {
        // Add a floor to the nearest Elevator (with appropriate state)
        Elevator nearest = Arrays.stream(elevators)
                .filter(e -> (up ? callingFloor >= e.currentFloor : callingFloor <= e.currentFloor))
                .filter(e -> e.state == (up ? State.UP : State.DOWN) || e.state == State.IDLE || e.state == State.UNLOAD)
                .min(Comparator.comparingInt(e -> Math.abs(e.currentFloor - callingFloor)))
                .orElse(null);
        // Cache save
        if (nearest == null) {
            ArrayList<Tuple<Integer, Integer>> tmp = up ? upCache : downCache;
            tmp.add(new Tuple<>(callingFloor, toFloor));
        } else {
            nearest.pickUp(callingFloor, toFloor);
        }
    }

    public void step() {
        // Flush cache
        if (!upCache.isEmpty()) {
            Arrays.stream(elevators)
                    .filter(e -> e.state == State.IDLE)
                    .findFirst()
                    .ifPresent(e -> {
                        e.bulkPickUp(upCache);
                        upCache.clear();
                    });
        }
        if (!downCache.isEmpty()) {
            Arrays.stream(elevators)
                    .filter(e -> e.state == State.IDLE)
                    .findFirst()
                    .ifPresent(e -> {
                        e.bulkPickUp(downCache);
                        downCache.clear();
                    });
        }
        for (Elevator e : elevators) e.step();
    }

    @Override
    public String toString() {
        StringBuilder elevatorsString = new StringBuilder();
        Arrays.stream(elevators).forEach(e -> elevatorsString.append(e.toString())); // TODO
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
