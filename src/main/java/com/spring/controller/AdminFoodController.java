package com.spring.controller;


import com.spring.model.Food;
import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.request.CreateFoodRequest;
import com.spring.response.MessageReponse;
import com.spring.service.FoodService;
import com.spring.service.RestaurantService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{foodId}")
    public ResponseEntity<Food> deleteFood(@PathVariable Long foodId,
                                           @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        foodService.deleteFood(foodId);
        MessageReponse res = new MessageReponse();
        res.setMessage("Food deleted successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFoodStock(@PathVariable Long foodId,
                                                @RequestHeader("Authorization") String token,
                                                @RequestBody Long quantity) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Food food = foodService.updateFoodStock(foodId, quantity);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
