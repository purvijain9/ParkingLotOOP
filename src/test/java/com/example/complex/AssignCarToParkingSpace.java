package com.example.complex;

import com.example.utils.ReadExcelData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AssignCarToParkingSpace {
    private ParkingComplex parkingComplex;
    private List<Car> cars;
    private List<List<List<Object>>> parkingComplexData;

    @BeforeClass
    public void setupData(){
        cars = new ArrayList<>();
        for (int i=0; i<50; i++){
            cars.add(new Car("100"+i));
        }
        readParkingComplexData();
        parkingComplex = new ParkingComplex();
        List<Floor> floorList = new ArrayList<>();
        for (List<Object> floorData : parkingComplexData.get(0)){
            Floor floor = new Floor();
            floor.setFloorNumber((String) floorData.get(0));
            int parkingSpaces = (int)((double) floorData.get(1));
            List<ParkingSpace> parkingSpaceList = new ArrayList<>();
            int i=0;
            while (i<parkingSpaces){
                parkingSpaceList.add(new ParkingSpace());
                i++;
            }
            floor.setParkingSpaces(parkingSpaceList);
            floorList.add(floor);
        }
        parkingComplex.setFloors(floorList);
    }

    private void readParkingComplexData(){
        try {
            parkingComplexData = ReadExcelData.readDataFromExcel("ParkingComplexDataExcel");
            parkingComplexData.get(0).remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assignCarToEmptyParkingSpace(){
        int floorNumber = 0;
        for (Car car : cars){
            while (floorNumber < parkingComplex.getFloors().size()){
                boolean flag = false;
                Floor floor = parkingComplex.getFloors().get(floorNumber);
                int parkingSpaceNumber = 0;
                while (parkingSpaceNumber < floor.getParkingSpaces().size()){
                    ParkingSpace parkingSpace = floor.getParkingSpaces().get(parkingSpaceNumber);
                    if (parkingSpace.isEmpty()){
                        parkingSpace.setAssignedCar(car);
                        flag = true;
                        break;
                    } else {
                        parkingSpaceNumber ++;
                    }
                }
                if (flag == true){
                    break;
                }
                floorNumber ++;
            }
        }
        for (Floor floor : parkingComplex.getFloors()){
            printDataOfFloor(floor);
        }
    }

    private void printDataOfFloor(Floor floor){
        System.out.println("Floor No. : " + floor.getFloorNumber());
        int totalParkingSpaces = floor.getParkingSpaces().size();
        int count = 0;
        for (ParkingSpace parkingSpace : floor.getParkingSpaces()){
            if (parkingSpace.isEmpty()){
                count ++;
            }
        }
        System.out.println("Total Parking Spaces Occupied : " + (totalParkingSpaces - count));
        System.out.println("Total Parking Spaces Empty : " + count);
    }
}
