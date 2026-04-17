package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.ReportResponse;
import com.buchi.petfinder.model.Adoption;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.AdoptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final AdoptionRepository adoptionRepository;
    private final PetService petService;

    public ReportService(AdoptionRepository adoptionRepository,
                         PetService petService) {
        this.adoptionRepository = adoptionRepository;
        this.petService = petService;
    }

    public ReportResponse generateReport(String fromDate, String toDate) {

        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);

        List<Adoption> adoptions = adoptionRepository.findAll()
                .stream()
                .filter(a -> {
                    LocalDate d = LocalDate.parse(a.getAdoptionDate());
                    return !d.isBefore(from) && !d.isAfter(to);
                })
                .toList();

        // FIX: get pet type correctly
        Map<String, Long> petTypes = adoptions.stream()
                .collect(Collectors.groupingBy(
                        a -> petService.getPetEntityById(a.getPetId()).getType(),
                        Collectors.counting()
                ));

        Map<String, Long> weekly = adoptions.stream()
                .collect(Collectors.groupingBy(
                        a -> {
                            LocalDate d = LocalDate.parse(a.getAdoptionDate());
                            return d.with(WeekFields.ISO.dayOfWeek(), 1).toString();
                        },
                        Collectors.counting()
                ));

        ReportResponse res = new ReportResponse();
        res.setAdoptedPetTypes(petTypes);
        res.setWeeklyAdoptionRequests(weekly);

        return res;
    }
}