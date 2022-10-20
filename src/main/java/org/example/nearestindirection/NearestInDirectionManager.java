package org.example.nearestindirection;

import org.example.elevatormanager.GroupManager;
import org.example.states.Direction;
import org.example.utils.Triplet;

import java.util.ArrayList;
import java.util.Comparator;

public class NearestInDirectionManager extends GroupManager<NearestInDirectionElevator> {
    private final ArrayList<Triplet<Integer, Boolean, Integer>> upCache = new ArrayList<>();
    private final ArrayList<Triplet<Integer, Boolean, Integer>> downCache = new ArrayList<>();


    @Override
    public void populateElevatorsArray(int numberOfElevators) {
        elevators = new ArrayList<>(numberOfElevators);
        for (int i = 0; i < numberOfElevators; i++) elevators.add(new NearestInDirectionElevator(i, 0));
    }

    @Override
    public NearestInDirectionElevator getSelectedElevator(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        return elevators.stream()
                // don't consider elevators which are "running away" from the callingFloor
                .filter(e -> !(e.getCurrentFloor() > callingFloor && e.getDirection() == Direction.UP))
                .filter(e -> !(e.getCurrentFloor() < callingFloor && e.getDirection() == Direction.DOWN))
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - callingFloor)))
                .orElse(null);
    }

    @Override
    public void doIfElevatorIsNull(int callingFloor, boolean upButtonPressed, Integer toFloor) {
        ArrayList<Triplet<Integer, Boolean, Integer>> tmp = upButtonPressed ? upCache : downCache;
        tmp.add(new Triplet<>(callingFloor, upButtonPressed, toFloor));
    }

    @Override
    public void beforeStep() {

    }

    @Override
    public void afterStep() {
        if (!upCache.isEmpty()) flushCache(upCache);
        if (!downCache.isEmpty()) flushCache(downCache);
    }

    private void flushCache(ArrayList<Triplet<Integer, Boolean, Integer>> cache) {
        if (cache != upCache && cache != downCache)
            throw new IllegalArgumentException("cache must be either upCache or downCache");
        elevators.stream()
                .filter(e -> e.getDirection() == Direction.NONE)
                .findFirst()
                .ifPresent(e -> {
                    e.bulkPickUp(cache);
                    cache.clear();
                });
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
