package com.spring.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long itemId;
    private int quantity;
    private List<String> ingredients;

}
