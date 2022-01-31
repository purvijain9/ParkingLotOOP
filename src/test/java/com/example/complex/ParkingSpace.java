package com.example.complex;

public class ParkingSpace {
    private boolean isEmpty = true;
    private Car assignedCar;

    public boolean isEmpty() {
        return isEmpty;
    }

    private void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void setAssignedCar(Car car){
        this.assignedCar = car;
        setEmpty(false);
    }
}
