package com.buchi.petfinder.dto;

import lombok.Data;
import java.util.List;

@Data
public class PetsResponse {
    private String status = "success";
    private List<PetSearchDTO> pets;
}
