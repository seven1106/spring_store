package com.spring.service;

import com.spring.model.*;
import com.spring.repository.AddressRepository;
import com.spring.repository.OrderItemRepository;
import com.spring.repository.OrderRepository;
import com.spring.repository.UserRepository;
import com.spring.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;


    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address shippingAddress = orderRequest.getDeliveryAddress();
        Address address = addressRepository.save(shippingAddress);
        if (!user.getAddresses().contains(address)) {
            user.getAddresses().add(address);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(orderRequest.getRestaurantId());
        Order order = new Order();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(address);
        order.setCreatedAt(new Date());
        order.setStatus("PENDING");
        Cart cart = cartService.FindCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setIngredients(item.getIngredients());
            orderItem.setTotalPrice(item.getTotalPrice());
            OrderItem saveOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(saveOrderItem);
        }
        Long totalPrice = cartService.calculateTotal(cart);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        Order saveOrder = orderRepository.save(order);
        restaurant.getOrders().add(saveOrder);

        return saveOrder;
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) throws Exception {
        Order order = findOrderById(orderId);
        order.setStatus(status);
        if (status.equals("OUT_FOR_DELIVERY") || status.equals("DELIVERED")
                || status.equals("COMPLETED") || status.equals("PENDING")) throw new Exception("Invalid status");
        {
            order.setStatus(status);
            return orderRepository.save(order);
        }
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(order.getId());


    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return List.of();
    }


    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId, String status) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (status != null) {
            orders = orders.stream().filter(order -> order.getStatus().equals(status)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}
