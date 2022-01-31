package com.example.complex;

public class Car {
    private String carNumber;

    public Car(String carNumber){
        setCarNumber(carNumber);
    }
    public String getCarNumber() {
        return carNumber;
    }

    private void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
