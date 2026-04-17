package com.buchi.petfinder.controller;

import com.buchi.petfinder.dto.*;
import com.buchi.petfinder.service.PetSearchService;
import com.buchi.petfinder.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PetController {

    private final PetService petService;
    private final PetSearchService petSearchService;

    public PetController(PetService petService,
                         PetSearchService petSearchService) {
        this.petService = petService;
        this.petSearchService = petSearchService;
    }

    // ✅ REQUIRED: create_pet
    @PostMapping("/create_pet")
    public ResponseEntity<PetIdResponse> createPet(@ModelAttribute PetCreateRequest req) {
        return ResponseEntity.ok(petService.createPet(req));
    }

    // ✅ REQUIRED: get_pets
    @PostMapping("/get_pets")
    public ResponseEntity<PetsResponse> getPets(@RequestBody SearchRequest req) {

        List<PetSearchDTO> pets = petSearchService.searchPets(req);

        PetsResponse response = new PetsResponse();
        response.setPets(pets);

        return ResponseEntity.ok(response);
    }
}