package com.buchi.petfinder.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetSearchDTO {
    private String petId;
    private String source; // "local" or "petfinder"
    private String type;
    private String gender;
    private String size;
    private String age;
    private Boolean goodWithChildren;
    private List<String> photos;
}