package com.spring.controller;

import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.request.CreateRestaurantRequest;
import com.spring.response.MessageReponse;
import com.spring.service.RestaurantService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String token,
                                                       @PathVariable Long restaurantId) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId, req);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<MessageReponse> deleteRestaurant(@RequestBody CreateRestaurantRequest req,
                                                           @RequestHeader("Authorization") String token,
                                                           @PathVariable Long restaurantId) throws Exception {
        User user = userService.findUserByJwtToken(token);
        restaurantService.deleteRestaurant(restaurantId);
        MessageReponse res = new MessageReponse();
        res.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/{restaurantId}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestBody CreateRestaurantRequest req,
                                                             @RequestHeader("Authorization") String token,
                                                             @PathVariable Long restaurantId) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(restaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Restaurant>> findRestaurantByUserId(@RequestBody CreateRestaurantRequest req,
                                                                   @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Restaurant> restaurant = restaurantService.getRestaurantsByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
