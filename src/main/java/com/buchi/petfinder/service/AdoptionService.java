package com.buchi.petfinder.service;

import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.repository.AdoptionRepository;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;

    @Autowired
    public AdoptionService(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    public List<Adoption> getAllAdoptions() {
        return adoptionRepository.findAll();
    }

    public Adoption getAdoptionById(String id) {
        return adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));
    }

    public Adoption createAdoption(Adoption adoption) {
        adoption.setStatus("Pending");
        return adoptionRepository.save(adoption);
    }

    public Adoption updateAdoptionStatus(String id, String status) {
        Adoption existingAdoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));

        existingAdoption.setStatus(status);
        return adoptionRepository.save(existingAdoption);
    }

    public void deleteAdoption(String id) {
        Adoption existingAdoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));

        adoptionRepository.delete(existingAdoption);
    }

    public List<Adoption> getAdoptionsByCustomer(String customerId) {
        return adoptionRepository.findByCustomerId(customerId);
    }

    public List<Adoption> getAdoptionsByPet(String petId) {
        return adoptionRepository.findByPetId(petId);
    }
}