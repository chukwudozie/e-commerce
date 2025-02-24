package dev.saha.productsservice.controller;

import dev.saha.productsservice.dto.ProductPurchaseRequest;
import dev.saha.productsservice.dto.ProductPurchaseResponse;
import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.dto.ProductResponse;
import dev.saha.productsservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("get-all/")
    public ResponseEntity<List<ProductResponse>> getProductById() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("purchase-products")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct (@RequestBody List<ProductPurchaseRequest> request){
        return ResponseEntity.ok(productService.purchaseProduct(request));
    }



}
