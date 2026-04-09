package com.buchi.petfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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
    private String adoptionDate; // e.g., "2026-04-09"
    private String status; // "Pending", "Approved", "Rejected"
}