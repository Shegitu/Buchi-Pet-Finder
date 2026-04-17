package com.buchi.petfinder.dto;

import lombok.Data;

@Data
public class CustomerIdResponse {
    private String status = "success";
    private String customerId;
}
