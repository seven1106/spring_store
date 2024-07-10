package com.spring.service;

import com.spring.model.Category;
import com.spring.model.Food;
import com.spring.model.Restaurant;
import com.spring.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest food, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getFoodsByCategory(Category category);

    public List<Food> getRestaurantFoods(Long restaurantId,
                                           Boolean isVeg,
                                           Boolean isNonVeg,
                                           boolean isSeasonal,
                                           String foodCategory);

    public List<Food> searchFoods(String query) throws Exception;

    public Food getFoodById(Long foodId) throws Exception;

    public Food updateFoodStock(Long foodId, Long quantity) throws Exception;

}
