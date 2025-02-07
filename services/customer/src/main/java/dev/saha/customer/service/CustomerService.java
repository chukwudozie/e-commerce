package dev.saha.customer.service;

import dev.saha.customer.dto.CustomerRequest;
import jakarta.validation.Valid;

import java.util.Map;

public interface CustomerService {
    Map<String, String> createCustomer(@Valid CustomerRequest request);
}
