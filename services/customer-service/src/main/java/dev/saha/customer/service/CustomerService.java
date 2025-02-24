package dev.saha.customer.service;

import dev.saha.customer.dto.CustomerRequest;
import dev.saha.customer.dto.CustomerResponse;
import dev.saha.customer.dto.UpdateRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    Map<String, Object> createCustomer(@Valid CustomerRequest request);

    Map<String, Object> updateCustomer(@Valid UpdateRequest request);

    List<CustomerResponse> getAllCustomers();

    boolean customerExists(String id);

    Map<String, Object> findCustomerById(String id);

    Map<String, Object> deleteCustomer(String id);
}
