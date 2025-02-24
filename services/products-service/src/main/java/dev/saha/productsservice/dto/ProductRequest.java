package dev.saha.productsservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public record  ProductRequest(
        @NotBlank(message = "Product name is required")
        String name,
        @NotBlank(message = "Product description is required")
        String description,
        @Positive(message = "Available quantity must be greater positive")
        double availableQuantity,
        @NotNull(message = "Product price is required")
        @Positive(message = "Price should be positive")
        BigDecimal price,
        @NotNull(message = "category id is required")
        @Positive(message = "Category id should be positive")
        Long categoryId

) {

}
