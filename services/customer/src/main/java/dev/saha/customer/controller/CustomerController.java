package dev.saha.customer.controller;

import dev.saha.customer.dto.CustomerRequest;
import dev.saha.customer.dto.CustomerResponse;
import dev.saha.customer.dto.UpdateRequest;
import dev.saha.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Map<String,Object>> createCustomer (@RequestBody @Valid CustomerRequest request){
        Map<String,Object> response = customerService.createCustomer(request);
        return (response.containsKey("error")) ? ResponseEntity.badRequest().body(response) :
                ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map<String,Object>> updateCustomer (@RequestBody @Valid UpdateRequest request){
        Map<String,Object> response = customerService.updateCustomer(request);
        return (response.containsKey("error")) ? ResponseEntity.badRequest().body(response) :
                ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers (){
        List<CustomerResponse> response = customerService.getAllCustomers();
        return (!response.isEmpty()) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}
