package com.buchi.petfinder.dto;

import java.util.Map;

public class ReportResponse {
    private Map<String, Long> adoptedPetTypes;
    private Map<String, Long> weeklyAdoptionRequests;

    public Map<String, Long> getAdoptedPetTypes() {
        return adoptedPetTypes;
    }

    public void setAdoptedPetTypes(Map<String, Long> adoptedPetTypes) {
        this.adoptedPetTypes = adoptedPetTypes;
    }

    public Map<String, Long> getWeeklyAdoptionRequests() {
        return weeklyAdoptionRequests;
    }

    public void setWeeklyAdoptionRequests(Map<String, Long> weeklyAdoptionRequests) {
        this.weeklyAdoptionRequests = weeklyAdoptionRequests;
    }
}