package com.buchi.petfinder.service;

import com.buchi.petfinder.exception.ResourceNotFoundException;
import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.model.Customer;
import com.buchi.petfinder.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final PetService petService;
    private final CustomerService customerService;

    @Autowired
    public AdoptionService(AdoptionRepository adoptionRepository, PetService petService, CustomerService customerService) {
        this.adoptionRepository = adoptionRepository;
        this.petService = petService;
        this.customerService = customerService;
    }

    public List<Adoption> getAllAdoptions() {
        List<Adoption> adoptions = adoptionRepository.findAll();
        adoptions.forEach(this::populatePetAndCustomer);
        return adoptions;
    }

    public Adoption getAdoptionById(String id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));
        populatePetAndCustomer(adoption);
        return adoption;
    }

    public Adoption createAdoption(Adoption adoption) {
        Pet pet = petService.getPetById(adoption.getPetId());
        Customer customer = customerService.getCustomerById(adoption.getCustomerId());

        boolean duplicate = adoptionRepository.findByPetId(adoption.getPetId())
                .stream()
                .anyMatch(a -> a.getCustomerId().equals(adoption.getCustomerId()) && a.getStatus().equals("Pending"));
        if (duplicate) {
            throw new RuntimeException("Customer already has a pending adoption for this pet.");
        }

        adoption.setStatus("Pending");
        adoption.setAdoptionDate(LocalDate.now().toString());
        Adoption saved = adoptionRepository.save(adoption);
        populatePetAndCustomer(saved);
        return saved;
    }

    public Adoption updateAdoptionStatus(String id, String status) {
        if (!status.equals("Pending") && !status.equals("Approved") && !status.equals("Rejected")) {
            throw new RuntimeException("Invalid status. Allowed: Pending, Approved, Rejected");
        }

        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));
        adoption.setStatus(status);
        Adoption updated = adoptionRepository.save(adoption);
        populatePetAndCustomer(updated);
        return updated;
    }

    public void deleteAdoption(String id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption with id " + id + " not found"));
        adoptionRepository.delete(adoption);
    }

    public List<Adoption> getAdoptionsByCustomer(String customerId) {
        List<Adoption> adoptions = adoptionRepository.findByCustomerId(customerId);
        adoptions.forEach(this::populatePetAndCustomer);
        return adoptions;
    }

    public List<Adoption> getAdoptionsByPet(String petId) {
        List<Adoption> adoptions = adoptionRepository.findByPetId(petId);
        adoptions.forEach(this::populatePetAndCustomer);
        return adoptions;
    }

    public List<Adoption> getAdoptionsByStatus(String status) {
        List<Adoption> adoptions = adoptionRepository.findByStatus(status);
        adoptions.forEach(this::populatePetAndCustomer);
        return adoptions;
    }

    public List<Pet> filterPets(String type, Integer minAge, Integer maxAge) {
        return petService.getAllPets().stream()
                .filter(p -> (type == null || p.getType().equalsIgnoreCase(type)))
                .filter(p -> (minAge == null || p.getAge() >= minAge))
                .filter(p -> (maxAge == null || p.getAge() <= maxAge))
                .collect(Collectors.toList());
    }

    private void populatePetAndCustomer(Adoption adoption) {
        adoption.setPet(petService.getPetById(adoption.getPetId()));
        adoption.setCustomer(customerService.getCustomerById(adoption.getCustomerId()));
    }
}