package dao;

import models.Foodtype;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oFoodtypeDaoTest {

    private Sql2oFoodtypeDao foodtypeDao;
    private Connection conn;

    Foodtype setupFoodtype() {
        return new Foodtype("GOOD");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_SetsUniqueId_true() throws Exception {
        Foodtype testType = setupFoodtype();
        int originalId = testType.getId();
        foodtypeDao.add(testType);
        assertNotEquals(originalId, testType.getId());

    }

    @Test
    public void getAll_returnsAllFoodtypes_true() throws Exception {
        Foodtype foodtype = setupFoodtype();
        Foodtype newFoodtype = setupFoodtype();
        Foodtype controlFoodType = setupFoodtype();
        controlFoodType.setName("Bad");
        foodtypeDao.add(foodtype);
        foodtypeDao.add(newFoodtype);
        assertEquals(2,foodtypeDao.getAll().size());
        assertFalse(foodtypeDao.getAll().contains(controlFoodType));
    }

    @Test
    public void deleteByIt_removesSpecificInstance_true() throws Exception {
        Foodtype testType = setupFoodtype();
        Foodtype controlType = setupFoodtype();
        testType.setName("BAD");
        foodtypeDao.add(testType);
        foodtypeDao.add(controlType);
        int idToDelete = testType.getId();
        foodtypeDao.deleteById(idToDelete);
        assertEquals(1, foodtypeDao.getAll().size());
        assertFalse(foodtypeDao.getAll().contains(testType));
    }
}