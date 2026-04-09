package com.buchi.petfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private String id;
    private String name;
    private String type;
    private int age;
}