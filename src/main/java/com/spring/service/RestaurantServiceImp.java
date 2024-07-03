package com.spring.service;

import com.spring.dto.RestaurantDto;
import com.spring.model.Address;
import com.spring.model.Restaurant;
import com.spring.model.User;
import com.spring.repository.AddressRepository;
import com.spring.repository.RestaurantRepository;
import com.spring.repository.UserRepository;
import com.spring.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setImages(req.getImages());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest req) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(req.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(req.getDescription());
        }
        if (restaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(req.getOpeningHours());
        }
        if (restaurant.getImages() != null) {
            restaurant.setImages(req.getImages());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(req.getName());
        }
        if (restaurant.getAddress() != null) {
            restaurant.setAddress(req.getAddress());
        }
        if (restaurant.getContactInformation() != null) {
            restaurant.setContactInformation(req.getContactInformation());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() throws Exception {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new Exception("Restaurant not found by id" + restaurantId);
        }
        return restaurant.get();
    }

    @Override
    public List<Restaurant> getRestaurantsByUserId(Long userId) throws Exception {
        List<Restaurant> restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant.isEmpty()) {
            throw new Exception("Restaurant not found by user id" + userId);
        }
        return restaurant;
    }


}
