package utils;

import dbfacades.DemoFacade;
import entity.Car;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Tester {

    public static void main(String[] args) {
        Tester t = new Tester();
        t.deleteCarByIDTest();
    }

    public void getAllCarsTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        List<Car> cars = d.getAllCars();
        System.out.println("All Cars: " + cars.get(1) + " Array Size: " + cars.size());
    }

    public void getCarsByMakeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        Car a = d.getCarsByMake("WW");
        System.out.println("Car by make: " + a.getMake());
    }

    public void getCarsByIDTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        Car a = d.getCarByID("1");
        System.out.println("Car with ID 1: " + a.getMake());
    }

    public void deleteCarByIDTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        int id = 1;
        d.deleteCarByID(id);
        try{
            d.getCarByID(Integer.toString(id));
        }
        catch(Exception e){
            System.out.println("No such car. Removal worked.");
        }
        Car a = new Car("Volvo");
        a.setId(id);
        d.addCar(a);
    }
}
