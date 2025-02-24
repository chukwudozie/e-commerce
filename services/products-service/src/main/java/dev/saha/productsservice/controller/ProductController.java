package dev.saha.productsservice.controller;

import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.model.Product;
import dev.saha.productsservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static dev.saha.productsservice.util.Constants.ERROR;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody @Valid ProductRequest request) {
        Map<String,Object> response = productService.createProduct(request);
        return (response.containsKey(ERROR)) ? ResponseEntity.badRequest().body(response) :
                new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


}
