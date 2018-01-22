package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    Restaurant setupRestaurant() {
        return new Restaurant("name", "address", "zipcode", "phone", "website", "email");
    }

    @Test
    public void gettersAndSetters_workCorrectly_true() {
        Restaurant testRestaurant = setupRestaurant(); //will test getters and setters
        Restaurant controlRestaurant = setupRestaurant(); //control object
        //change all values of testRestaurant with setters
        testRestaurant.setName("THIS");
        testRestaurant.setAddress("TEST");
        testRestaurant.setZipcode("WILL");
        testRestaurant.setPhone("PASS");
        testRestaurant.setWebsite("ALL");
        testRestaurant.setEmail("DAY");
        testRestaurant.setId(100);
        assertTrue(controlRestaurant.getName().equals("name"));
        assertTrue(controlRestaurant.getAddress().equals("address"));
        assertTrue(controlRestaurant.getZipcode().equals("zipcode"));
        assertTrue(controlRestaurant.getPhone().equals("phone"));
        assertTrue(controlRestaurant.getWebsite().equals("website"));
        assertTrue(controlRestaurant.getEmail().equals("email"));
        assertTrue(testRestaurant.getName().equals("THIS"));
        assertTrue(testRestaurant.getAddress().equals("TEST"));
        assertTrue(testRestaurant.getZipcode().equals("WILL"));
        assertTrue(testRestaurant.getPhone().equals("PASS"));
        assertTrue(testRestaurant.getWebsite().equals("ALL"));
        assertTrue(testRestaurant.getEmail().equals("DAY"));
        assertEquals(100, testRestaurant.getId());
    }


}