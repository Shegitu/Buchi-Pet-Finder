package com.buchi.petfinder.controller;

import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.model.Customer;
import com.buchi.petfinder.service.AdoptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdoptionControllerTest {

    @Mock
    private AdoptionService adoptionService;

    @InjectMocks
    private AdoptionController adoptionController;

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
    void testGetAllAdoptions() {
        when(adoptionService.getAllAdoptions()).thenReturn(List.of(adoption));
        List<Adoption> result = adoptionController.getAllAdoptions();
        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getPet().getName());
    }

    @Test
    void testGetAdoptionById() {
        when(adoptionService.getAdoptionById("a1")).thenReturn(adoption);
        ResponseEntity<Adoption> response = adoptionController.getAdoptionById("a1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getCustomer().getName());
    }

    @Test
    void testCreateAdoption() {
        when(adoptionService.createAdoption(any())).thenReturn(adoption);
        Adoption newAdoption = adoptionController.createAdoption(adoption);
        assertEquals("Pending", newAdoption.getStatus());
    }

    @Test
    void testUpdateAdoptionStatus() {
        when(adoptionService.updateAdoptionStatus("a1", "Approved")).thenReturn(adoption);
        ResponseEntity<Adoption> response = adoptionController.updateAdoptionStatus("a1", "Approved");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteAdoption() {
        doNothing().when(adoptionService).deleteAdoption("a1");
        ResponseEntity<Void> response = adoptionController.deleteAdoption("a1");
        assertEquals(204, response.getStatusCodeValue());
    }
}