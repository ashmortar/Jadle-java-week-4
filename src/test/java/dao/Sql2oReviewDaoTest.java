package dao;

import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.runtime.events.RecompilationEvent;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.*;


public class Sql2oReviewDaoTest {

    private Sql2oReviewDao reviewDao;
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oFoodtypeDao foodtypeDao;
    private Connection conn;


    Review setupReview() {
        return new Review("writtenBy", 10, "content", 1);
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add_ReviewSetsUniqueId_true() throws Exception {
        Review testReview = setupReview();
        int originalId = testReview.getId();
        reviewDao.add(testReview);
        assertNotEquals(originalId, testReview.getId());
    }

    @Test
    public void getAll_ReturnsAllReviewObjects_true() throws Exception {
        Review testReview = setupReview();
        Review secondTestReview = setupReview();
        Review controlReview = setupReview();
        controlReview.setRating(5);
        reviewDao.add(testReview);
        reviewDao.add(secondTestReview);
        assertEquals(2, reviewDao.getAll().size());
        assertFalse(reviewDao.getAll().contains(controlReview));
    }

    @Test
    public void getAllReviewsByRestaurantId_returnsCorrectDataSet_true() throws Exception {
        Review testReview = setupReview();
        Review secondTestREview = setupReview();
        Review controlReview = setupReview();
        testReview.setRestaurantId(2);
        secondTestREview.setRestaurantId(2);
        reviewDao.add(testReview);
        reviewDao.add(secondTestREview);
        reviewDao.add(controlReview);
        assertEquals(2, reviewDao.getAllReviewsByRestaurant(2).size());
    }

    @Test
    public void deleteByIt_removesSpecifiedInstanceOfReview_true() throws Exception {
        Review testREview = setupReview();
        Review controlReview = setupReview();
        testREview.setWrittenBy("fred");
        reviewDao.add(testREview);
        reviewDao.add(controlReview);
        reviewDao.deleteById(1);
        assertEquals(1,reviewDao.getAll().size());
        assertTrue(reviewDao.getAll().contains(controlReview));
    }
}