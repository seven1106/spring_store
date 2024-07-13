package com.spring.controller;


import com.spring.model.Cart;
import com.spring.model.CartItem;
import com.spring.model.User;
import com.spring.request.AddCartItemRequest;
import com.spring.request.UpdateCartItemQuantityRequest;
import com.spring.service.CartService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PutMapping("/cart-item/add-item")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update-quantity")
    public ResponseEntity<CartItem> updateItemQuantity(@RequestBody UpdateCartItemQuantityRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateItemQuantity(req.getCartItemId(), req.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/remove-item/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws Exception {
        cartService.removeItemFromCart(cartItemId, jwt);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/clear-cart")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.FindCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
