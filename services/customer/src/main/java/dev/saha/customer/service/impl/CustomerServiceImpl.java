package dev.saha.customer.service.impl;

import dev.saha.customer.dto.CustomerRequest;
import dev.saha.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Map<String, String> createCustomer(CustomerRequest request) {
        return Map.of();
    }
}
