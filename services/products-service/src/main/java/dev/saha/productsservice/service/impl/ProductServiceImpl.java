package dev.saha.productsservice.service.impl;

import dev.saha.productsservice.dto.ProductPurchaseRequest;
import dev.saha.productsservice.dto.ProductPurchaseResponse;
import dev.saha.productsservice.dto.ProductRequest;
import dev.saha.productsservice.dto.ProductResponse;
import dev.saha.productsservice.exception.CustomException;
import dev.saha.productsservice.exception.NotFoundException;
import dev.saha.productsservice.mapper.ProductsMapper;
import dev.saha.productsservice.model.Category;
import dev.saha.productsservice.model.Product;
import dev.saha.productsservice.repository.CategoryRepository;
import dev.saha.productsservice.repository.ProductRepository;
import dev.saha.productsservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductsMapper mapper;

    @Override
    public Map<String, Object> createProduct(ProductRequest request) {
        Product product = new Product();
        try {
            BeanUtils.copyProperties(request, product);
            Long categoryId = request.categoryId();
            validateId(categoryId, "Category Id is required");

            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

            if (optionalCategory.isEmpty()) {
                throw new NotFoundException(String.format("Category with id %s not found", categoryId));
            }

            Category category = optionalCategory.get();
            product.setCategory(category);
            Product newProduct = productRepository.save(product);
            ProductResponse response = mapper.toProductResponse(newProduct);
            return Map.of("product", response);
        } catch (Exception e) {
            log.error("An exception occurred while creating the product {}", e.getMessage());
            return Map.of("error", e.getMessage());
        }

    }

    @Override
    public ProductResponse getProductById(Long id) {
        validateId(id, "Customer Id is required");
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return mapper.toProductResponse(existingProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse response = ProductResponse.builder().build();
            BeanUtils.copyProperties(product, response);
            response.setCategoryName(product.getCategory().getName());
            response.setCategoryDescription(product.getCategory().getDescription());

            productResponses.add(response);
        });
        return productResponses;
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        log.info("Product purchase request :: {}", request);
        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        List<Long> productIds = request.stream()
                .map(ProductPurchaseRequest::getProductId)
                .toList();
        log.info("Product Ids from request :: {}", productIds);
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new CustomException("One or more Products does not exist in store", 417);
        }
        List<ProductPurchaseRequest> sortedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::getProductId))
                .toList();
        for (int i = 0; i < storedProducts.size(); i++) {
            Product storedProduct = getProduct(storedProducts, i, sortedRequest);
            productRepository.save(storedProduct);
            ProductPurchaseResponse purchased = mapper.toProductPurchaseResponse(storedProduct);
            purchasedProducts.add(purchased);
        }
        return purchasedProducts;
    }

    private static Product getProduct(List<Product> storedProducts, int i, List<ProductPurchaseRequest> sortedRequest) {
        Product storedProduct = storedProducts.get(i);
        ProductPurchaseRequest productRequest = sortedRequest.get(i);
        if (storedProduct.getAvailableQuantity() < productRequest.getQuantity()) {
            throw new CustomException("Insufficient Stock quantity for product with ID " + productRequest.getProductId(), 417);
        }
        double newAvailableQuantity = storedProduct.getAvailableQuantity() - productRequest.getQuantity();
        storedProduct.setAvailableQuantity(newAvailableQuantity);
        return storedProduct;
    }


    private void validateId(Long id, String message) {
        if (Objects.isNull(id)) {
            throw new CustomException(message, 400);
        }
    }
}
