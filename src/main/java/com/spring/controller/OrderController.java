package com.spring.controller;

import com.spring.model.Order;
import com.spring.model.User;
import com.spring.request.OrderRequest;
import com.spring.service.OrderService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest req) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping("/user-orders")
    public ResponseEntity<List<Order>> getHistoryOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
