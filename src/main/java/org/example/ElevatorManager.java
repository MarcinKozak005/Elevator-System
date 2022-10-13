package org.example;

import org.example.utils.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ElevatorManager {

    byte numberOfElevators;
    short numberOfPositiveFloors; // Num of floors above the ground level. Including ground level (0)
    short numberOfNegativeFloors; // Num of floors below the ground level.
    Elevator[] elevators;
    ArrayList<Short> upCache = new ArrayList<>();
    ArrayList<Short> downCache = new ArrayList<>();


    ElevatorManager(byte numberOfElevators, short numberOfPositiveFloors, short numberOfNegativeFloors){
        this.numberOfElevators = numberOfElevators;
        this.numberOfPositiveFloors = numberOfPositiveFloors;
        this.numberOfNegativeFloors = numberOfNegativeFloors;
        this.elevators = new Elevator[numberOfElevators];
        for(int i=0; i<numberOfElevators;i++) this.elevators[i] = new Elevator(i,(short) 0,(short) 0);
    }

    public List<Triplet<Integer,Short, Short>> status(){
        return Arrays.stream(elevators).map(Elevator::getStatus).collect(Collectors.toList());
    }

    public void pickUp(short callingFloor, boolean up, short toFloor){
        // Add a floor to the nearest Elevator (with appropriate state)
        Elevator nearest = Arrays.stream(elevators)
                .filter(e -> e.state == (up ? State.UP : State.DOWN) || e.state == State.IDLE)
                .min(Comparator.comparingInt(e -> Math.abs(e.currentFloor - callingFloor)))
                .orElse(null);
        // Cache save
        if (nearest == null){
            ArrayList<Short> tmp = up?upCache:downCache;
            tmp.add(callingFloor);
        } else {
            nearest.pickUp(callingFloor, toFloor);
        }
        // Flush cache
        if (!upCache.isEmpty()) Arrays.stream(elevators)
                .filter(e -> e.state == State.UP)
                .findFirst()
                .ifPresent(e -> e.floorsOrder.addAll(upCache));
        if (!downCache.isEmpty()) Arrays.stream(elevators)
                .filter(e -> e.state == State.DOWN)
                .findFirst()
                .ifPresent(e -> e.floorsOrder.addAll(downCache));
    }

    public void step(){
        for (Elevator e: elevators) e.step();
    }

    @Override
    public String toString() {
        return "ElevatorManager{" +
                "numberOfElevators=" + numberOfElevators +
                "\nnumberOfPositiveFloors=" + numberOfPositiveFloors +
                "\nnumberOfNegativeFloors=" + numberOfNegativeFloors +
                "\nelevators=" + Arrays.toString(elevators) +
                "\nupCache=" + upCache +
                "\ndownCache=" + downCache +
                '}';
    }
}
