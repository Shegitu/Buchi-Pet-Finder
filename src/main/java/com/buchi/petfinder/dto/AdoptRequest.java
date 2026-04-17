package com.buchi.petfinder.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AdoptRequest {
    @NotBlank private String customerId;
    @NotBlank private String petId;
}

