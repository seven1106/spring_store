package com.spring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    private Long totalAmount;
    private String status;
    private Date createdAt;
    @ManyToOne
    private Address address;
    @OneToMany
    private List<OrderItem> orderItems;
    private int totalItems;
    private int totalPrice;

}
