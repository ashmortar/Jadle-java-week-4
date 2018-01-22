package dao;

import models.Review;

import java.util.List;


public interface ReviewDao {

    //create
    void add(Review review); // F
    //read

    List<Review> getAllReviewsByRestaurant(int restaurantId); // H
    List<Review> getAll();


    //update
    //delete
    void delete(int reviewId); // L
}
