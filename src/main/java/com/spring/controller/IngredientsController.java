package com.spring.controller;

import com.spring.model.IngredientCategory;
import com.spring.model.IngredientsItem;
import com.spring.request.IngredientsRequest;
import com.spring.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(String name, Long restaurantId) {
        try {
            IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(name, restaurantId);
//            return ResponseEntity.ok(ingredientCategory);
            return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/item")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientsRequest ingredientsItemRequest) {
        try {
            IngredientsItem ingredientCategory = ingredientsService.createIngredientItem(ingredientsItemRequest.getName(), ingredientsItemRequest.getCategoryId(), ingredientsItemRequest.getRestaurantId());
            return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }


    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) {
        try {
            IngredientsItem ingredientsItem = ingredientsService.updateStock(id);
            return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredients(@PathVariable Long restaurantId) throws Exception {
        List<IngredientCategory> ingredientCategories = ingredientsService.findRestaurantIngredients(restaurantId);
        return ResponseEntity.ok(ingredientCategories);
    }
    @GetMapping("/restaurant/{restaurantId}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategories(@PathVariable Long restaurantId) throws Exception {
            List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoriesByRestaurantId(restaurantId);
            return ResponseEntity.ok(ingredientCategories);

    }

}
