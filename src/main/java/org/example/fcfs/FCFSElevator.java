package org.example.fcfs;

import org.example.Elevator;

import java.util.ArrayDeque;
import java.util.Deque;

public class FCFSElevator extends Elevator {
    private Deque<Integer> queue = new ArrayDeque<>();

    public FCFSElevator(int id, int currentFloor) {
        super(id, currentFloor);
    }

    @Override
    protected void elevatorSpecificPickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        queue.add(fromFloor);
        if (toFloor != null) queue.add(toFloor);
    }

    @Override
    protected void elevatorSpecificPickUp(int toFloor) {
        queue.add(toFloor);
    }


    @Override
    protected void elevatorSpecificStep() {
        if (!queue.isEmpty()) {
            if (currentFloor == queue.peekFirst()) queue.pollFirst();
            else if (currentFloor < queue.peekFirst()) currentFloor++;
            else if (currentFloor > queue.peekFirst()) currentFloor--;
        }
    }

    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public Integer getDestinationFloor() {
        return (queue.isEmpty() ? null : queue.peekFirst());
    }
}
