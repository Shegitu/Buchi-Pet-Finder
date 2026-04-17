package com.buchi.petfinder.controller;

import com.buchi.petfinder.dto.ReportRequest;
import com.buchi.petfinder.dto.ReportResponse;
import com.buchi.petfinder.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ✅ BONUS: generate_report
    @PostMapping("/generate_report")
    public ResponseEntity<?> generate(@RequestBody ReportRequest request) {

        ReportResponse data = reportService.generateReport(
                request.getFromDate(),
                request.getToDate()
        );

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "data", data
                )
        );
    }
}