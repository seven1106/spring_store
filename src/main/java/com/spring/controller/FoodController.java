package com.spring.controller;

import com.spring.model.Food;
import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.request.CreateFoodRequest;
import com.spring.service.FoodService;
import com.spring.service.RestaurantService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/foods")

public class FoodController {

        @Autowired
        private FoodService foodService;
        @Autowired
        private UserService userService;
        @Autowired
        private RestaurantService restaurantService;

        @GetMapping("/search")
        public ResponseEntity<List<Food>> searchFood(@RequestParam String name) throws Exception {
            List<Food> foods = foodService.searchFoods(name);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
        @GetMapping("restaurant/{restaurantId}")
        public ResponseEntity<List<Food>> getRestaurantFoods(@PathVariable Long restaurantId,
                                                             @RequestParam(required = false) Boolean isVeg,
                                                             @RequestParam(required = false) Boolean isNonVeg,
                                                             @RequestParam(required = false) boolean isSeasonal,
                                                             @RequestParam(required = false) String foodCategory) {
            List<Food> foods = foodService.getRestaurantFoods(restaurantId, isVeg, isNonVeg, isSeasonal, foodCategory);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
}
