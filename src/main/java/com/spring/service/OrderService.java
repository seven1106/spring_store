package com.spring.service;

import com.spring.model.Order;
import com.spring.model.User;
import com.spring.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest orderRequest, User user) throws Exception;
    public Order updateOrderStatus(Long orderId, String status) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getOrdersByUserId(Long userId);
    public List<Order> getOrdersByRestaurantId(Long restaurantId, String status);
    public Order findOrderById(Long orderId) throws Exception;

}
