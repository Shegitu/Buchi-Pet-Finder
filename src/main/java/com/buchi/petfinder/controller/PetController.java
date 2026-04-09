package com.buchi.petfinder.controller;

import com.buchi.petfinder.dto.PetDTO;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable String id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }
    
    @GetMapping("/search")
public ResponseEntity<List<PetDTO>> searchPets(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String name) {

    return ResponseEntity.ok(petService.searchPets(type, age, name));
}

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.createPet(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable String id, @RequestBody Pet petDetails) {
        return ResponseEntity.ok(petService.updatePet(id, petDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(@PathVariable String id) {
        petService.deletePet(id);
        return ResponseEntity.ok("Pet deleted successfully");
    }
}