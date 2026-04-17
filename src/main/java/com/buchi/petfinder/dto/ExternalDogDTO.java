package com.buchi.petfinder.dto;

import lombok.Data;

@Data
public class ExternalDogDTO {
    private String id;
    private String name;
    private String breed;
    private String temperament;
    private String life_span;
    private String origin;
}