package com.spring.service;

import com.spring.model.Cart;
import com.spring.model.CartItem;
import com.spring.model.Food;
import com.spring.model.User;
import com.spring.repository.CartItemRepository;
import com.spring.repository.CartRepository;
import com.spring.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;


    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            throw new Exception("User not found");
        }
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(user);
            cart = cartRepository.save(cart);
        }
        Food food = foodService.getFoodById(req.getItemId());
        for (CartItem item : cart.getCartItems()) {
            if (item.getFood().equals(food)) {
                int newQuantity = item.getQuantity() + req.getQuantity();
                return updateItemQuantity(item.getId(), newQuantity);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setCart(cart);
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(food.getPrice() * req.getQuantity());
        CartItem saveCartItem = cartItemRepository.save(cartItem);
        cart.getCartItems().add(saveCartItem);
        return saveCartItem;
    }

    @Override
    public Cart removeItemFromCart(Long itemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            throw new Exception("User not found");
        }
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            throw new Exception("Cart not found");
        }
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
        if (optionalCartItem.isEmpty()) {
            throw new Exception("Cart Item not found");
        }
        CartItem cartItem = optionalCartItem.get();
        cart.getCartItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public CartItem updateItemQuantity(Long itemId, int quantity) throws Exception {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);
        if (optionalCartItem.isEmpty()) {
            throw new Exception("Cart Item not found");
        }
        CartItem cartItem = optionalCartItem.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Long calculateTotal(Cart cart) throws Exception {
        long total = 0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getFood().getPrice() * item.getQuantity();
        }

        return total;
    }

    @Override
    public Cart FindCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new Exception("Cart not found");
        }
        return optionalCart.get();
    }

    @Override
    public Cart FindCartByUserId(Long id) throws Exception {
        Cart cart = cartRepository.findByCustomerId(id);
        if (cart == null) {
            throw new Exception("Cart not found");
        }
        cart.setTotal(calculateTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long id) throws Exception {
        Cart cart = FindCartByUserId(id);
        if (cart == null) {
            throw new Exception("Cart not found");
        }
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}
