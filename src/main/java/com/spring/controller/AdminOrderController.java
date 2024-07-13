package com.spring.controller;

import com.spring.model.Order;
import com.spring.model.User;
import com.spring.service.OrderService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable Long restaurantId,
                                                               @RequestHeader("Authorization") String jwt,
                                                               @RequestParam(required = false) String status)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOrdersByRestaurantId(restaurantId, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/update-status/{orderId}/{status}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestHeader("Authorization") String jwt,
                                                   @PathVariable String status) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
