package dev.saha.productsservice.dto;

import dev.saha.productsservice.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class ProductResponse {
    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
    private String categoryName;
    private String categoryDescription;

}
