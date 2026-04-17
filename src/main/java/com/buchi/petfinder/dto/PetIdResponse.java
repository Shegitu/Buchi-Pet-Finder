package com.buchi.petfinder.dto;

import lombok.Data;

@Data
public class PetIdResponse {
    private String status = "success";
    private String petId;
}
