package dev.saha.customer.mapper;

import dev.saha.customer.dto.CustomerRequest;
import dev.saha.customer.dto.CustomerResponse;
import dev.saha.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CustomerMapper {

    public Optional<Customer> toCustomer(CustomerRequest customerRequest) {
        if (Objects.isNull(customerRequest)) {
            log.error("CustomerRequest is null");
            return Optional.empty();
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        return Optional.of(customer);
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        CustomerResponse response = CustomerResponse.builder().build();
        BeanUtils.copyProperties(customer,response);
        return response;
    }
}
