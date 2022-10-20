package org.example;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {

    List<GroupManager<? extends Elevator>> groups;

    public SystemManager(){};

    public SystemManager(GroupManager<? extends Elevator> eg){
        this.groups = new ArrayList<>();
        groups.add(eg);

    }
    public SystemManager(List<GroupManager<? extends Elevator>> groups) {
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

    public void addGroup(GroupManager<? extends Elevator> group){
        if (groups == null){
            this.groups = new ArrayList<>();
            groups.add(group);
        }
        groups.add(group);
    }

    public void step(){
        groups.forEach(GroupManager::step);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<groups.size();i++) sb.append("\n---Group ").append(i).append("---\n").append(groups.get(i));
        return "SystemManager{\n" +
                "groups=\n" + sb +
                '}';
    }
}
