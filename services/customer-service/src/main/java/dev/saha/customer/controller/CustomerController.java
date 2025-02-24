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

import static dev.saha.customer.util.Constants.ERROR;

@RestController
@RequestMapping("/api/v1/customer")
//@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createCustomer (@RequestBody @Valid CustomerRequest request){
        Map<String,Object> response = customerService.createCustomer(request);
        return (response.containsKey(ERROR)) ? ResponseEntity.badRequest().body(response) :
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
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Map<String, Boolean>> customerExists (@PathVariable String id){
        boolean response = customerService.customerExists(id);
        return  ResponseEntity.ok(Map.of("exists", response));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Map<String, Object>> fetchById (@PathVariable  String id){
        Map<String,Object> response  = customerService.findCustomerById(id);
        return (response.containsKey("error")) ? ResponseEntity.badRequest().body(response) :
                ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteCustomer (@PathVariable String id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
