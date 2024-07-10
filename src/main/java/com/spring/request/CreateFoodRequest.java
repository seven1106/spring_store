package com.spring.request;

import com.spring.model.Category;
import com.spring.model.IngredientsItem;

import java.util.List;

public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean isVeg;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients;

}
