package dao;


import models.Foodtype;
import models.Restaurant;
import models.Review;

import java.util.List;

public interface RestaurantDao {

    //create
    void add (Restaurant restaurant); // J
    //void addRestaurantToFoodType(Restaurant restaurant, Foodtype foodtype); // D & E


    //read
    List<Restaurant> getAll();
    List<Restaurant> getAllByZipcode(String zipcode); // A
    Restaurant findById(int id); // B

    int averageRating (int restaurantId); // G


    //List<Restaurant> getAllRestaurantsByFoodType(int foodtypeId); //E



    //update
    void update(int id, String name, String address, String zipcode, String phone, String website, String email); // K


    //delete
    void deleteById(int restaurantId); // J
}
