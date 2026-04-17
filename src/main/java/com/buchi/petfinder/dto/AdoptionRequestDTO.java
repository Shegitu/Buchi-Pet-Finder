package com.buchi.petfinder.dto;

import lombok.Data;

@Data
public class AdoptionRequestDTO {
    private String customerId;
    private String customerPhone;
    private String customerName;
    private String petId;
    private String type;
    private String gender;
    private String size;
    private String age;
    private Boolean goodWithChildren;
}

