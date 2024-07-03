package com.spring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    private User owner;
    @OneToOne
    private Address address;
    private String description;
    private String cuisineType;
    @Embedded
    private ContactInformation contactInformation;
    private String openingHours;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order>orders = new ArrayList<>();
    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> menu = new ArrayList<>();
    private Boolean isOpen;


}
