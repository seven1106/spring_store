package com.spring.repository;

import com.spring.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE f.name LIKE %:query% " +
            "OR f.foodCategory.name LIKE %:query%")
    List<Food> searchFood(@Param("query") String query);
}
