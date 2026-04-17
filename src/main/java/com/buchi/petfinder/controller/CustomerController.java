package com.buchi.petfinder.controller;

import com.buchi.petfinder.dto.AddCustomerRequest;
import com.buchi.petfinder.dto.CustomerIdResponse;
import com.buchi.petfinder.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ✅ REQUIRED: add_customer
    @PostMapping("/add_customer")
    public CustomerIdResponse addCustomer(@RequestBody AddCustomerRequest req) {
        return customerService.addCustomer(req.getName(), req.getPhone());
    }
}