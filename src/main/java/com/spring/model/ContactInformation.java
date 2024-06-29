package com.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
public class ContactInformation {
    private String email;
    private String phoneNumber;
    private String twitter;
    private String instagram;

}
