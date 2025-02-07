package dev.saha.customer.service.impl;

import dev.saha.customer.dto.CustomerRequest;
import dev.saha.customer.dto.CustomerResponse;
import dev.saha.customer.dto.UpdateRequest;
import dev.saha.customer.exception.CustomException;
import dev.saha.customer.exception.NotFoundException;
import dev.saha.customer.mapper.CustomerMapper;
import dev.saha.customer.model.Customer;
import dev.saha.customer.repository.CustomerRepository;
import dev.saha.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Map<String, Object> createCustomer(CustomerRequest request) {
        log.info("Create customer request: {}", request);
        try {
            Optional<Customer> customer = mapper.toCustomer(request);
            if (customer.isPresent()) {
                Customer savedCustomer = repository.save(customer.get());
                log.info("Customer saved: {}", savedCustomer);
                return Map.of("customer", savedCustomer);
            } else {
                log.info("Failed to save customer");
                return Map.of("error", "Failed to save customer");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Map.of("error", e.getMessage());
        }
    }


    @Override
    public Map<String, Object> updateCustomer(UpdateRequest request) {
        log.info("Update customer request: {},", request);
        try {
            if (request.id().trim().isEmpty()) {
                return Map.of("error", "Invalid id");
            }
            Customer existingCustomer = repository.findById(request.id())
                    .orElseThrow(() -> new NotFoundException("Failed to find customer with id: " + request.id()));
            mergeCustomer(existingCustomer, request);
            repository.save(existingCustomer);
            return Map.of("customer", existingCustomer);
        }catch (Exception e) {
            log.error(e.getMessage());
            return Map.of("error", e.getMessage());
        }

    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> customerResponses =  repository.findAll()
                .stream()
                .map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
        if (customerResponses.isEmpty()) {
            throw new NotFoundException("Failed to find all customers");
        }
        return customerResponses;

    }

    private void validateId(String id){
        if (Objects.isNull(id) || id.trim().isEmpty()) {
            throw new CustomException("Customer id is required",400);
        }
    }

    @Override
    public boolean customerExists(String id) {
        validateId(id);
        return repository.existsById(id);
    }

    @Override
    public Map<String, Object> findCustomerById(String id) {
        validateId(id);
        Customer customer =  repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Failed to get customer with id : %s", id)));
        return Map.of("customer", customer);
    }

    @Override
    public Map<String, Object> deleteCustomer(String id) {
        validateId(id);
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return Map.of("response", "Customer deleted successfully");

        }
        return Map.of("error", "Customer not found");
    }

    private void mergeCustomer(Customer existingCustomer, UpdateRequest request) {
        if (StringUtils.isNotBlank(request.firsName())) {
            existingCustomer.setFirstName(request.firsName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            existingCustomer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            existingCustomer.setEmail(request.email());
        }
        if(Objects.nonNull(request.address())){
            existingCustomer.setAddress(request.address());
        }
    }
}
