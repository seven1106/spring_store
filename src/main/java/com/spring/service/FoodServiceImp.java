package com.spring.service;

import com.spring.model.Category;
import com.spring.model.Food;
import com.spring.model.Restaurant;
import com.spring.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(Food food, Category category, Restaurant restaurant) {
        Food newFood = new Food();
        newFood.setName(food.getName());
        newFood.setDescription(food.getDescription());
        newFood.setPrice(food.getPrice());
        newFood.setIsVeg(food.getIsVeg());
        newFood.setIsSeasonal(food.getIsSeasonal());
        newFood.setFoodCategory(category);
        newFood.setRestaurant(restaurant);
        newFood.setImages(food.getImages());
        newFood.setIngredients(food.getIngredients());
        Food saveFood = foodRepository.save(newFood);
        restaurant.getMenu().add(saveFood);
        return saveFood;

    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = getFoodById(foodId);
        if (food == null) {
            throw new Exception("Food not found");
        }
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getFoodsByCategory(Category category) {
        return List.of();
    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId, Boolean isVeg, Boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVeg) {
            foods = filterFoodsByVeg(foods, isVeg);
        }
        if (isNonVeg) {
            foods = filterFoodsByNonVeg(foods, isNonVeg);
        }
        if (isSeasonal) {
            foods = filterFoodsBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterFoodsByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterFoodsByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterFoodsBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.getIsSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterFoodsByNonVeg(List<Food> foods, Boolean isNonVeg) {
        return foods.stream().filter(food -> food.getIsVeg() == false).collect(Collectors.toList());
    }

    private List<Food> filterFoodsByVeg(List<Food> foods, Boolean isVeg) {
        return foods.stream().filter(food -> food.getIsVeg() == isVeg).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFoods(String query) throws Exception{
        return foodRepository.searchFood(query);
    }

    @Override
    public Food getFoodById(Long foodId) throws Exception{
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new Exception("Food not found by id" + foodId);
        }
        return food.get();
    }

    @Override
    public Food updateFoodStock(Long foodId, Long quantity) throws Exception {
        Food food = getFoodById(foodId);
        if (food == null) {
            throw new Exception("Food not found");
        }
        food.setIsAvailable(!food.getIsAvailable());
        return foodRepository.save(food);
    }
}
