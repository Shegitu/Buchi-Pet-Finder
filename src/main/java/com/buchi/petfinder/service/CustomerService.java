package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.CustomerIdResponse;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import com.buchi.petfinder.model.Customer;
import com.buchi.petfinder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    public CustomerIdResponse addCustomer(String name, String phone) {

        return customerRepository.findByPhone(phone)
                .map(existing -> {
                    CustomerIdResponse res = new CustomerIdResponse();
                    res.setCustomerId(existing.getId());
                    return res;
                })
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setName(name);
                    c.setPhone(phone);

                    Customer saved = customerRepository.save(c);

                    CustomerIdResponse res = new CustomerIdResponse();
                    res.setCustomerId(saved.getId());
                    return res;
                });
    }

    public Customer getCustomerEntityById(String id) {
        return getCustomerById(id);
    }

    public void deleteCustomer(String id) {
        Customer c = getCustomerById(id);
        customerRepository.delete(c);
    }
}