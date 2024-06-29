package com.spring.model;

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
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Long price;
    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    @ManyToOne
    private Category foodCategory;
    private Boolean isAvailable;
    @ManyToOne
    private Restaurant restaurant;
    private Boolean isVeg;
    private Boolean isSeasonal;
    @ManyToMany
    private List<IngredientsItem> ingredients;
    private Date createdAt;
}



