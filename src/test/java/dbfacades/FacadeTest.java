package dbfacades;

import dbfacades.DemoFacade;
import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * UNIT TEST example that mocks away the database with an in-memory database See
 * Persistence unit in persistence.xml in the test packages
 *
 * Use this in your own project by: - Delete everything inside the setUp method,
 * but first, observe how test data is created - Delete the single test method,
 * and replace with your own tests
 *
 */
public class FacadeTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);

    DemoFacade facade = new DemoFacade(emf);

    /**
     * Setup test data in the database to a known state BEFORE Each test
     */
    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete all, since some future test cases might add/change data
            em.createQuery("delete from Car").executeUpdate();
            //Add our test data
            Car e1 = new Car("Volve");
            Car e2 = new Car("WW");
            em.persist(e1);
            em.persist(e2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Test the single method in the Facade
    @Test
    public void countEntities() {
        long count = facade.countCars();
        Assert.assertEquals(2, count);
    }

    @Test
    public void getAllCarsTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        List<Car> cars = facade.getAllCars();
        Assert.assertEquals(3, cars.size());
    }

    @Test
    public void getCarsByMakeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        Car a = d.getCarsByMake("WW");
        Assert.assertTrue(a.getMake().equals("WW"));
    }

    @Test
    public void getCarsByIDTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        String id = Integer.toString(d.getAllCars().get(0).getId());
        Car a = d.getCarByID(id); 
        Assert.assertTrue(a.getId().equals(Integer.parseInt(id)));
    }

    @Test
    public void deleteCarByIDTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade d = new DemoFacade(emf);
        int id = d.getAllCars().get(d.getAllCars().size()-1).getId();
        d.deleteCarByID(id);
        Car a = new Car("Volvo");
        a.setId(id);
        d.addCar(a);
    }

}
