package com.buchi.petfinder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")

public class Pet {
    @Id
    private String id;
    private String name;
    private String type;
    private int age;
    private String owner;
}