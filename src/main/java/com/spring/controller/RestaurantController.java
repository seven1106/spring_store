package com.spring.controller;

import com.spring.dto.RestaurantDto;
import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.service.RestaurantService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String query,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurant = restaurantService.searchRestaurants(query);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addRestaurantToFavorites(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        RestaurantDto restaurant = restaurantService.addRestaurantToFavorites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
