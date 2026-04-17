package com.buchi.petfinder.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class PetCreateRequest {
    @NotBlank
    private String type;
    
    @NotBlank
    private String gender;
    
    @NotBlank
    private String size;
    
    @NotBlank
    private String age;
    
    @NotNull
    private Boolean goodWithChildren;
    
    private List<MultipartFile> photos;
}
