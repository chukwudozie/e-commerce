package dev.saha.productsservice.mapper;

import dev.saha.productsservice.dto.ProductPurchaseResponse;
import dev.saha.productsservice.dto.ProductResponse;
import dev.saha.productsservice.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductsMapper {

    public ProductPurchaseResponse toProductPurchaseResponse(Product product) {

        return ProductPurchaseResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .quantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .build();

    }


    public ProductResponse toProductResponse(Product product) {
        ProductResponse response = ProductResponse.builder().build();
        BeanUtils.copyProperties(product, response);
        response.setCategoryName(product.getCategory().getName());
        response.setCategoryDescription(product.getCategory().getDescription());
        return response;
    }
}
