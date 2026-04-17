package com.buchi.petfinder.dto;

import lombok.Data;
import java.util.List;

@Data
public class AdoptionRequestsResponse {
    private String status = "success";
    private List<AdoptionRequestDTO> data;
}

