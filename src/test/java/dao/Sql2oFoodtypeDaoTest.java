package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oFoodtypeDaoTest {

    private Sql2oFoodtypeDao foodtypeDao;
    private Sql2oRestaurantDao restaurantDao;
    private Connection conn;

    Foodtype setupFoodtype() {
        return new Foodtype("GOOD");
    }
    Restaurant setUpRestaurant() {
        return new Restaurant("Burrito Boy", "111 Burrito Lane ", "97408", "541-555-5555", "burrito@gmail.com", "www.burrito.com");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        restaurantDao = new Sql2oRestaurantDao(sql2o);
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
    @Test
    public void addFoodTypeToRestaurantAddsTypeCorrectly() throws Exception {

        Restaurant testRestaurant = setUpRestaurant();
        Restaurant altRestaurant = setUpRestaurant();

        restaurantDao.add(testRestaurant);
        restaurantDao.add(altRestaurant);

        Foodtype testFoodtype = setupFoodtype();

        foodtypeDao.add(testFoodtype);

        foodtypeDao.addFoodtypeToRestaurant(testFoodtype, testRestaurant);
        foodtypeDao.addFoodtypeToRestaurant(testFoodtype, altRestaurant);

        assertEquals(2, foodtypeDao.getAllRestaurantsForAFoodtype(testFoodtype.getId()).size());
    }

    @Test
    public void deleteingRestaurantAlsoUpdatesJoinTable() throws Exception {
        Foodtype testFoodtype  = new Foodtype("Seafood");
        foodtypeDao.add(testFoodtype);

        Restaurant testRestaurant = setUpRestaurant();
        restaurantDao.add(testRestaurant);

        Restaurant altRestaurant = setUpRestaurant();
        restaurantDao.add(altRestaurant);

        restaurantDao.addRestaurantToFoodtype(testRestaurant,testFoodtype);
        restaurantDao.addRestaurantToFoodtype(altRestaurant, testFoodtype);

        restaurantDao.deleteById(testRestaurant.getId());
        assertEquals(0, restaurantDao.getAllFoodtypesForARestaurant(testRestaurant.getId()).size());
    }




}