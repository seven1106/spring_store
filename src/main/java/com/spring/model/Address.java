package com.spring.model;

import jakarta.persistence.*;

@Entity

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String city;
    private String zipCode;
}
