package dbfacades;

import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*
Simple Facade demo for this start-up project
 Use this in your own project by:
  - Rename this class to reflect your "business logic"
  - Delete the class entity.Car, and add your own entity classes
  - Delete the three public methods below, and replace with your own Facade Logic 
  - Delete all content in the main method

 */
public class DemoFacade {

    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Car addCar(Car car) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } finally {
            em.close();
        }
    }

    public List<Car> getAllCars() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("Select c from Car c");
        return q.getResultList();
    }

    public long countCars() {
        EntityManager em = emf.createEntityManager();
        try {
            return (Long) em.createQuery("select Count(m) from Car m").getSingleResult();
        } finally {
            em.close();
        }

    }

    /*
  This will only work when your have added a persistence.xml file in the folder: 
     src/main/resources/META-INF
  You can use the file: persistence_TEMPLATE.xml (in this folder) as your template
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade df = new DemoFacade(emf);
        df.addCar(new Car("Volvo"));
        df.addCar(new Car("WW"));
        df.addCar(new Car("Jaguar"));
        long numbOfCars = df.countCars();
        System.out.println("Number of cars: " + numbOfCars);
    }

    public Car getCarsByMake(String make) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("select c from Car c where c.make = :make").setParameter("make", make);
            return (Car) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Car getCarByID(String string) {
        EntityManager em = emf.createEntityManager();
        try {
            Car a = em.find(Car.class,
                    Integer.parseInt(string));
            return a;
        } finally {
            em.close();
        }
    }

    public void deleteCarByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Car where id = :id").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
    }

}
