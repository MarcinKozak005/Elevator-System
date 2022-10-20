package org.example.elevatormanager;

import org.example.elevator.Elevator;

import java.util.List;

public class SystemManager {

    List<ElevatorsGroup<? extends Elevator>> groups;

    public SystemManager(ElevatorsGroup<? extends Elevator> eg){
        this.groups = List.of(eg);

    }
    public SystemManager(List<ElevatorsGroup<? extends Elevator>> groups) {
        this.groups = groups;
    }

    private void validateGroupNumber(int groupNumber){
        if (groupNumber < 0 || groupNumber > groups.size())
            throw new IllegalArgumentException("There is no group with groupNumber=" + groupNumber);
    }


    public void pickUp(int groupNumber, int callingFloor, boolean upButtonPressed, Integer toFloor) {
        validateGroupNumber(groupNumber);
        groups.get(groupNumber).pickUp(callingFloor,upButtonPressed,toFloor);
    }

    public void pickUp(int groupNumber, int elevatorId, int toFloor){
        validateGroupNumber(groupNumber);
        groups.get(groupNumber).pickUp(elevatorId,toFloor);
    }

    public void addGroup(ElevatorsGroup<? extends Elevator> group){
        groups.add(group);
    }

    public void step(){
        groups.forEach(ElevatorsGroup::step);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<groups.size();i++) sb.append("---Group ").append(i).append("---\n").append(groups.get(i));
        return "SystemManager{\n" +
                "groups=\n" + sb +
                '}';
    }
}
