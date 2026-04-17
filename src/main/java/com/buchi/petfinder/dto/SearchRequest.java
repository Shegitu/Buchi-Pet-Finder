package com.buchi.petfinder.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import java.util.List;

@Data
public class SearchRequest {
    private List<String> type;
    private Boolean goodWithChildren;
    private List<String> age;
    private List<String> gender;
    private List<String> size;
    
    @Min(1)
    private Integer limit = 10;
}
