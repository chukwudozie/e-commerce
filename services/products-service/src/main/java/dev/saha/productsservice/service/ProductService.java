package dev.saha.productsservice.service;

import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.model.Product;

import java.util.Map;

public interface ProductService {


    Map<String,Object> createProduct(ProductRequest request);

    Product getProductById(Long id);
}
