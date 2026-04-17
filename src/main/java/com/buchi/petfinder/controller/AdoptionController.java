package com.buchi.petfinder.controller;

import com.buchi.petfinder.dto.*;
import com.buchi.petfinder.service.AdoptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    // ✅ REQUIRED: POST adopt
    @PostMapping("/adopt")
    public ResponseEntity<AdoptIdResponse> adopt(@RequestBody AdoptRequest req) {
        return ResponseEntity.ok(adoptionService.createAdoption(req));
    }

    // ✅ REQUIRED: GET adoption requests by date
    @GetMapping("/get_adoption_requests")
    public ResponseEntity<AdoptionRequestsResponse> getAdoptionRequests(
            @RequestParam String from_date,
            @RequestParam String to_date) {

        List<AdoptionRequestDTO> data =
                adoptionService.getRequestsByDateRange(from_date, to_date);

        AdoptionRequestsResponse response = new AdoptionRequestsResponse();
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}