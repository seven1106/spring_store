package com.spring.service;

import com.spring.model.IngredientCategory;
import com.spring.model.IngredientsItem;
import com.spring.model.Restaurant;
import com.spring.repository.IngredientCategoryRepository;
import com.spring.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optionalIngredientCategory = ingredientCategoryRepository.findById(id);
        if (optionalIngredientCategory.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }
        return optionalIngredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(String ingredientName, Long categoryId, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);
        if (ingredientCategory == null) {
            throw new Exception("Ingredient Category not found");
        }
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setIngredientCategory(ingredientCategory);
        item.setRestaurant(restaurant);
        IngredientsItem savedItem = ingredientsItemRepository.save(item);
        ingredientCategory.getIngredients().add(savedItem);

        return savedItem;
    }

    @Override
    public List<IngredientCategory> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long itemId) throws Exception {
        Optional<IngredientsItem> optionalItem = ingredientsItemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new Exception("Item not found");
        }
        IngredientsItem item = optionalItem.get();
        item.setInStock(!item.getInStock());
        return null;
    }
}
