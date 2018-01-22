package dao;


import models.Foodtype;
import models.Restaurant;
import models.Review;

import java.util.List;

public interface RestaurantDao {

    //create
    void add (Restaurant restaurant); // J
    void addRestaurantToFoodtype(Restaurant restaurant, Foodtype foodtype); // D & E


    //read
    List<Restaurant> getAll();
    List<Restaurant> getAllByZipcode(String zipcode); // A
    Restaurant findById(int id); // B

    int averageRating (int restaurantId); // G

    List<Foodtype> getAllFoodtypesForARestaurant(int restaurantId);; //E



    //update
    void update(int id, String name, String address, String zipcode, String phone, String website, String email); // K


    //delete
    void deleteById(int restaurantId); // J
}
