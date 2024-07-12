package com.spring.service;

import com.spring.model.IngredientCategory;
import com.spring.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {


    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception;
    public IngredientsItem createIngredientItem(String ingredientName, Long categoryId, Long restaurantId) throws Exception;
    public List<IngredientCategory> findRestaurantIngredients(Long restaurantId) throws Exception;
    public IngredientsItem updateStock(Long itemId) throws Exception;

}
