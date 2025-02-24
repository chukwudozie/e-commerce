package dev.saha.productsservice.service;

import dev.saha.productsservice.dto.ProductPurchaseRequest;
import dev.saha.productsservice.dto.ProductPurchaseResponse;
import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.dto.ProductResponse;

import java.util.List;
import java.util.Map;

public interface ProductService {


    Map<String,Object> createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request);
}
