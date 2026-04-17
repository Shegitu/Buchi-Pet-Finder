package com.buchi.petfinder.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AddCustomerRequest {
    @NotBlank private String name;
    @NotBlank private String phone;
}
