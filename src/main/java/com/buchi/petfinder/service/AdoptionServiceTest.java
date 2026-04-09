package com.buchi.petfinder.service;

import com.buchi.petfinder.exception.ResourceNotFoundException;
import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.model.Customer;
import com.buchi.petfinder.repository.AdoptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetService petService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AdoptionService adoptionService;

    private Adoption adoption;
    private Pet pet;
    private Customer customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        pet = new Pet("p1", "Buddy", "Dog", 3, "John");
        customer = new Customer("c1", "Alice", "alice@mail.com");
        adoption = new Adoption("a1", "p1", "c1", "2026-04-09", "Pending", pet, customer);
    }

    @Test
    void testGetAdoptionById_Success() {
        when(adoptionRepository.findById("a1")).thenReturn(Optional.of(adoption));
        when(petService.getPetById("p1")).thenReturn(pet);
        when(customerService.getCustomerById("c1")).thenReturn(customer);

        Adoption result = adoptionService.getAdoptionById("a1");
        assertEquals("Pending", result.getStatus());
        assertEquals("Buddy", result.getPet().getName());
        assertEquals("Alice", result.getCustomer().getName());
    }

    @Test
    void testGetAdoptionById_NotFound() {
        when(adoptionRepository.findById("x")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> adoptionService.getAdoptionById("x"));
    }

    @Test
    void testCreateAdoption_DuplicatePending() {
        when(petService.getPetById("p1")).thenReturn(pet);
        when(customerService.getCustomerById("c1")).thenReturn(customer);
        when(adoptionRepository.findByPetId("p1")).thenReturn(List.of(adoption));

        Adoption newAdoption = new Adoption(null, "p1", "c1", null, null, null, null);
        assertThrows(RuntimeException.class, () -> adoptionService.createAdoption(newAdoption));
    }

    @Test
    void testUpdateAdoptionStatus_InvalidStatus() {
        when(adoptionRepository.findById("a1")).thenReturn(Optional.of(adoption));
        assertThrows(RuntimeException.class, () -> adoptionService.updateAdoptionStatus("a1", "Unknown"));
    }

    @Test
    void testUpdateAdoptionStatus_Success() {
        when(adoptionRepository.findById("a1")).thenReturn(Optional.of(adoption));
        when(adoptionRepository.save(any())).thenReturn(adoption);
        when(petService.getPetById("p1")).thenReturn(pet);
        when(customerService.getCustomerById("c1")).thenReturn(customer);

        Adoption updated = adoptionService.updateAdoptionStatus("a1", "Approved");
        assertEquals("Approved", updated.getStatus());
    }

    @Test
    void testDeleteAdoption() {
        when(adoptionRepository.findById("a1")).thenReturn(Optional.of(adoption));
        doNothing().when(adoptionRepository).delete(adoption);
        assertDoesNotThrow(() -> adoptionService.deleteAdoption("a1"));
        verify(adoptionRepository, times(1)).delete(adoption);
    }
}