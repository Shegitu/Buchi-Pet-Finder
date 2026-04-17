package com.buchi.petfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "adoptions")
public class Adoption {

    @Id
    private String id;

    private String petId;
    private String customerId;
    private String adoptionDate;
    private String status;

    @Transient
    private Pet pet;

    @Transient
    private Customer customer;
}