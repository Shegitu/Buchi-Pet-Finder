package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.*;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.AdoptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final PetService petService;
    private final CustomerService customerService;

    public AdoptionService(AdoptionRepository adoptionRepository,
                           PetService petService,
                           CustomerService customerService) {
        this.adoptionRepository = adoptionRepository;
        this.petService = petService;
        this.customerService = customerService;
    }

    //CREATE ADOPTION (Endpoint 4)
    public AdoptIdResponse createAdoption(AdoptRequest req) {

        // validate existence
        petService.getPetById(req.getPetId());
        customerService.getCustomerById(req.getCustomerId());

        // prevent duplicate pending request
        boolean exists = adoptionRepository.findByPetId(req.getPetId())
                .stream()
                .anyMatch(a -> a.getCustomerId().equals(req.getCustomerId())
                        && "PENDING".equalsIgnoreCase(a.getStatus()));

        if (exists) {
            throw new RuntimeException("Duplicate adoption request");
        }

        Adoption adoption = new Adoption();
        adoption.setPetId(req.getPetId());
        adoption.setCustomerId(req.getCustomerId());
        adoption.setStatus("PENDING");
        adoption.setAdoptionDate(LocalDate.now().toString());

        Adoption saved = adoptionRepository.save(adoption);

        AdoptIdResponse res = new AdoptIdResponse();
        res.setAdoptionId(saved.getId());
        return res;
    }

    // GET ALL (extra but useful)
    public List<Adoption> getAllAdoptions() {
        List<Adoption> list = adoptionRepository.findAll();
        list.forEach(this::enrich);
        return list;
    }

    //  GET BY ID
    public Adoption getAdoptionById(String id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption not found"));
        enrich(adoption);
        return adoption;
    }

    // DELETE
    public void deleteAdoption(String id) {
        if (!adoptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adoption not found");
        }
        adoptionRepository.deleteById(id);
    }

    //  UPDATE STATUS
    public Adoption updateAdoptionStatus(String id, String status) {

        if (status == null ||
                (!status.equalsIgnoreCase("PENDING")
                        && !status.equalsIgnoreCase("APPROVED")
                        && !status.equalsIgnoreCase("REJECTED"))) {
            throw new RuntimeException("Invalid status");
        }

        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption not found"));

        adoption.setStatus(status.toUpperCase());

        Adoption updated = adoptionRepository.save(adoption);
        enrich(updated);
        return updated;
    }

    //  REQUIRED: get_adoption_requests (Endpoint 5)
    public List<AdoptionRequestDTO> getRequestsByDateRange(String fromDate, String toDate) {

        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);

        List<Adoption> list = adoptionRepository.findAll()
                .stream()
                .filter(a -> {
                    LocalDate d = LocalDate.parse(a.getAdoptionDate());
                    return !d.isBefore(from) && !d.isAfter(to);
                })
                .sorted((a, b) -> b.getAdoptionDate().compareTo(a.getAdoptionDate()))
                .collect(Collectors.toList());

        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    // EXTRA FILTERS 
    public List<Adoption> getAdoptionsByCustomer(String customerId) {
        return adoptionRepository.findByCustomerId(customerId);
    }

    public List<Adoption> getAdoptionsByPet(String petId) {
        return adoptionRepository.findByPetId(petId);
    }

    public List<Adoption> getAdoptionsByStatus(String status) {
        return adoptionRepository.findByStatus(status);
    }

    public List<Pet> filterPets(String type) {

       List<Pet> pets = adoptionRepository.findAll()
                .stream()
                .map(a -> petService.getPetEntityById(a.getPetId()))
                .collect(Collectors.toList());
        return pets.stream()
                 .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    } 

   
    
    // DTO CONVERSION
    private AdoptionRequestDTO toDto(Adoption a) {

        AdoptionRequestDTO dto = new AdoptionRequestDTO();

        dto.setCustomerId(a.getCustomerId());

        var customer = customerService.getCustomerEntityById(a.getCustomerId());
        dto.setCustomerName(customer.getName());
        dto.setCustomerPhone(customer.getPhone());

        dto.setPetId(a.getPetId());

        Pet pet = petService.getPetEntityById(a.getPetId());
        dto.setType(pet.getType());
        dto.setGender(pet.getGender());
        dto.setSize(pet.getSize());
        dto.setAge(pet.getAge());
        dto.setGoodWithChildren(pet.isGoodWithChildren());

        return dto;
    }

    // ENRICH DATA (attach pet + customer)
    private void enrich(Adoption adoption) {
        adoption.setPet(petService.getPetEntityById(adoption.getPetId()));
        adoption.setCustomer(customerService.getCustomerEntityById(adoption.getCustomerId()));
    }
} 