package com.spring.service;

import com.spring.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoriesByRestaurantId(Long restaurantId) throws Exception;

    public Category findCategoriesById(Long id) throws Exception;

}
