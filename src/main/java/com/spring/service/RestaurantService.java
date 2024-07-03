package com.spring.service;

import com.spring.dto.RestaurantDto;
import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception;
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest req) throws Exception;
    public void deleteRestaurant(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurants() throws Exception;
    public List<Restaurant> searchRestaurants(String query);
    public Restaurant findRestaurantById(Long restaurantId) throws Exception;
    public List<Restaurant> getRestaurantsByUserId(Long userId) throws Exception;


}
