package com.spring.service;

import com.spring.model.Cart;
import com.spring.model.CartItem;
import com.spring.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public Cart removeItemFromCart(Long itemId, String jwt) throws Exception;
    public CartItem updateItemQuantity(Long itemId, int quantity) throws Exception;
    public Long calculateTotal(Cart cart) throws Exception;
    public Cart FindCartById(Long id) throws Exception;
    public Cart FindCartByUserId(Long id) throws Exception;
    public Cart clearCart(Long id) throws Exception;
}
