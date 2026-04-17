package com.buchi.petfinder.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")
public class Pet {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private String gender;

    @NotBlank
    private String size;

    private String age;

    private boolean goodWithChildren;

    private String owner;

    private List<String> photos;

    private String source;
}