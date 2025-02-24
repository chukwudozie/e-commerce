package dev.saha.productsservice.service.impl;

import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.exception.CustomException;
import dev.saha.productsservice.model.Product;
import dev.saha.productsservice.repository.ProductRepository;
import dev.saha.productsservice.service.ProductService;
import jakarta.ws.rs.NotAcceptableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Map<String, Object> createProduct(ProductRequest request) {
        Product product = new Product();
        try {
            BeanUtils.copyProperties(request, product);
            Product newProduct  = productRepository.save(product);
            return Map.of("product", newProduct);
        }catch (Exception e){
            log.error("An exception occurred while creating the product {}", e.getMessage());
            return Map.of("error",  e.getMessage());
        }

    }

    @Override
    public Product getProductById(Long id) {
        validateId(id);
        return productRepository.findById(id)
                .orElseThrow(() -> new NotAcceptableException("Product not found"));
    }


    private void validateId(Long id){
        if (Objects.isNull(id)) {
            throw new CustomException("Customer id is required",400);
        }
    }
}
