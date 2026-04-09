package com.buchi.petfinder.controller;

import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @Autowired
    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping
    public List<Adoption> getAllAdoptions() {
        return adoptionService.getAllAdoptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adoption> getAdoptionById(@PathVariable String id) {
        return ResponseEntity.ok(adoptionService.getAdoptionById(id));
    }

    @PostMapping
    public Adoption createAdoption(@RequestBody Adoption adoption) {
        return adoptionService.createAdoption(adoption);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Adoption> updateAdoptionStatus(@PathVariable String id, @RequestParam String status) {
        return ResponseEntity.ok(adoptionService.updateAdoptionStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdoption(@PathVariable String id) {
        adoptionService.deleteAdoption(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public List<Adoption> getAdoptionsByCustomer(@PathVariable String customerId) {
        return adoptionService.getAdoptionsByCustomer(customerId);
    }

    @GetMapping("/pet/{petId}")
    public List<Adoption> getAdoptionsByPet(@PathVariable String petId) {
        return adoptionService.getAdoptionsByPet(petId);
    }
}