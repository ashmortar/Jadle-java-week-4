package dao;

import models.Foodtype;
import models.Restaurant;

import java.util.List;

public interface FoodtypeDao {

    //create
    void add (Foodtype foodtype);

    //read
    List<Foodtype> getAll();

    List <Foodtype> getAllFoodtypesByRestaurant (int restaurantId); // D


    //update
    //delete
    void deleteById (int id);
}
