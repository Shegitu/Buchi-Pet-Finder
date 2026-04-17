package com.buchi.petfinder.dto;

import lombok.Data;

@Data
public class AdoptIdResponse {
    private String status = "success";
    private String adoptionId;
}

