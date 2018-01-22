package dao;

import models.Foodtype;
import models.Restaurant;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;


public class Sql2oRestaurantDaoTest {

    private Sql2oRestaurantDao restaurantDao;
    private Sql2oReviewDao reviewDao;
    private Sql2oFoodtypeDao foodtypeDao;
    private Connection conn;

    Foodtype setupFoodtype() {
        return new Foodtype("GOOD");
    }

    Restaurant setUpRestaurant() {
        return new Restaurant("Burrito Boy", "111 Burrito Lane ", "97408", "541-555-5555", "burrito@gmail.com", "www.burrito.com");
    }

    Review setupReview() {
        return new Review("writtenBy", 10, "content", 1);
    }


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_RestaurantSetsUniqueId_true() throws Exception {
        Restaurant testRestaurant = setUpRestaurant();
        int originalId = testRestaurant.getId();
        restaurantDao.add(testRestaurant);
        assertNotEquals(originalId, testRestaurant.getId());
    }

    @Test
    public void getAllByZipcode_returnsAllInstancesWithMatchingZipcode_true() throws Exception {
        Restaurant testRest = setUpRestaurant();
        Restaurant secondTestRest = setUpRestaurant();
        Restaurant controlRest = setUpRestaurant();
        controlRest.setZipcode("12345");
        restaurantDao.add(testRest);
        restaurantDao.add(secondTestRest);
        restaurantDao.add(controlRest);
        assertEquals(2, restaurantDao.getAllByZipcode("97408").size());
        assertFalse(restaurantDao.getAllByZipcode("97408").contains(controlRest));
    }

    @Test
    public void findByIdFindsCorrectRestById() throws Exception {
        Restaurant testRest = setUpRestaurant();
        Restaurant secondRest = setUpRestaurant();
        testRest.setName("Burrito Girl");
        restaurantDao.add(testRest);
        restaurantDao.add(secondRest);
        assertEquals("Burrito Girl", restaurantDao.findById(1).getName());


    }

    @Test
    public void getAll_returnsAllRestaurants_true() throws Exception {
        Restaurant testRest = setUpRestaurant();
        Restaurant secondRest = setUpRestaurant();
        Restaurant controlRest = setUpRestaurant();
        controlRest.setName("boogie");
        restaurantDao.add(testRest);
        restaurantDao.add(secondRest);
        assertEquals(2, restaurantDao.getAll().size());
        assertFalse(restaurantDao.getAll().contains(controlRest));
    }

    @Test
    public void averageRating_returnAverageRating() throws Exception {
        Restaurant testRest = setUpRestaurant();
        restaurantDao.add(testRest);
        Review review = setupReview();
        Review newRev = setupReview();
        Review thirdRev = setupReview();
        Review fourthRev = setupReview();
        review.setRating(2);
        newRev.setRating(4);
        thirdRev.setRating(4);
        reviewDao.add(review);
        reviewDao.add(newRev);
        reviewDao.add(thirdRev);
        reviewDao.add(fourthRev);
        assertEquals(5, restaurantDao.averageRating(1));
    }

    @Test
    public void update_ChangesValuesCorrectly_trye() throws Exception {
        Restaurant testRest = setUpRestaurant();
        restaurantDao.add(testRest);
        restaurantDao.update(1, "TEST", "PASSES", "ALL", "DAY", "LONG", "BRUH");
        assertEquals("TEST", restaurantDao.findById(1).getName());
        assertEquals("PASSES", restaurantDao.findById(1).getAddress());
        assertEquals("ALL", restaurantDao.findById(1).getZipcode());
        assertEquals("DAY", restaurantDao.findById(1).getPhone());
        assertEquals("LONG", restaurantDao.findById(1).getWebsite());
        assertEquals("BRUH", restaurantDao.findById(1).getEmail());

    }

    @Test
    public void deleteById_removeSpecificRestaurant_true() throws Exception {
        Restaurant restaurant = setUpRestaurant();
        Restaurant newRest = setUpRestaurant();
        restaurantDao.add(restaurant);
        restaurantDao.add(newRest);
        restaurantDao.deleteById(1);
        assertEquals(1, restaurantDao.getAll().size());
        assertFalse(restaurantDao.getAll().contains(restaurant));
    }

    @Test
    public void getAllFoodtypesForARestaurantReturnsFoodtypesCorrectly() throws Exception {
        Foodtype testFoodtype = new Foodtype("Seafood");
        foodtypeDao.add(testFoodtype);

        Foodtype otherFoodtype = new Foodtype("Bar Food");
        foodtypeDao.add(otherFoodtype);

        Restaurant testRestaurant = setUpRestaurant();
        restaurantDao.add(testRestaurant);
        restaurantDao.addRestaurantToFoodtype(testRestaurant, testFoodtype);
        restaurantDao.addRestaurantToFoodtype(testRestaurant, otherFoodtype);

        Foodtype[] foodtypes = {testFoodtype, otherFoodtype}; //oh hi what is this?

        assertEquals(restaurantDao.getAllFoodtypesForARestaurant(testRestaurant.getId()), Arrays.asList(foodtypes));
    }

    @Test
    public void deletingFoodtypeAlsoUpdatesJoinTable_true() throws Exception {
        Restaurant testRest = setUpRestaurant();
        restaurantDao.add(testRest);

        Foodtype foodOne = new Foodtype("Mexican");
        foodtypeDao.add(foodOne);

        Foodtype foodTwo = new Foodtype("DRINKIES");
        foodtypeDao.add(foodTwo);

        foodtypeDao.addFoodtypeToRestaurant(foodOne, testRest);
        foodtypeDao.addFoodtypeToRestaurant(foodTwo, testRest);

        foodtypeDao.deleteById(foodOne.getId());
        assertEquals(0, foodtypeDao.getAllRestaurantsForAFoodtype(foodOne.getId()).size());
    }
}

