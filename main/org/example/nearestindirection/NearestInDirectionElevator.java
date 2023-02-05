package org.example.nearestindirection;

import org.example.Elevator;
import org.example.states.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.TreeSet;

/**
 * Elevator realising the NearestInDirection scheduling
 * It stops on the floors on which there is a pickup order in the same direction that the elevator is going
 */
public class NearestInDirectionElevator extends Elevator {
    private Direction direction = Direction.NONE;
    private final List<Integer> buffer = new ArrayList<>();
    private TreeSet<Integer> queue = new TreeSet<>();

    public NearestInDirectionElevator(int id, int currentFloor) {
        super(id, currentFloor);
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public OptionalInt getDestinationFloor() {
        return (queue.isEmpty() ? OptionalInt.empty() : OptionalInt.of(queue.first()));
    }

    @Override
    public String toString() {
        return super.toString() + ", direction=" + direction + ", floorsOrder=" + queue + "}\n";
    }

    @Override
    protected void elevatorSpecificPickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        if (toFloor == null) {
            toFloor = fromFloor;
            if (queue.isEmpty()) {
                configureQueueOrder(upButtonPressed);
                direction = (upButtonPressed) ? Direction.UP : Direction.DOWN;
            }
            queue.add(toFloor);
        } else if (queue.isEmpty()) {
            configureQueueOrder(upButtonPressed);
            direction = (upButtonPressed) ? Direction.UP : Direction.DOWN;
            queue.add(toFloor);
        } else if (fromFloor < toFloor && !upButtonPressed || fromFloor > toFloor && upButtonPressed) {
            buffer.add(toFloor);
        } else queue.add(toFloor);
        queue.add(fromFloor);
    }

    @Override
    protected void elevatorSpecificPickUp(int toFloor) {
        if (queue.isEmpty()) {
            configureQueueOrder(currentFloor < toFloor);
            direction = (currentFloor < toFloor) ? Direction.UP : Direction.DOWN;
            queue.add(toFloor);
        } else if (direction == Direction.UP && currentFloor > toFloor ||
                direction == Direction.DOWN && currentFloor < toFloor)
            buffer.add(toFloor);
        else
            queue.add(toFloor);
    }

    @Override
    protected void elevatorSpecificStep() {
        if (!queue.isEmpty()) {
            if (currentFloor == queue.first()) queue.pollFirst();
            else if (currentFloor < queue.first()) currentFloor++;
            else if (currentFloor > queue.first()) currentFloor--;
        }
        if (queue.isEmpty() && !buffer.isEmpty()) {
            // Take inquiries from the buffer
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
            configureQueueOrder(direction == Direction.UP);
            queue.addAll(buffer);
            buffer.clear();
        } else if (queue.isEmpty()) {
            direction = Direction.NONE;
        }
    }

    private void configureQueueOrder(boolean upButtonPressed) {
        queue = (upButtonPressed) ? new TreeSet<>() : new TreeSet<>((o1, o2) -> Integer.compare(o2, o1));
    }
}
