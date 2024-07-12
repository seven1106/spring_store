package com.spring.request;

import lombok.Data;

@Data
public class IngredientsRequest {
    String name;
    private Long categoryId;
    private Long restaurantId;

}
