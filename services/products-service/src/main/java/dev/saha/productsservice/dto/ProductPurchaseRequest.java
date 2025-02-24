package dev.saha.productsservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductPurchaseRequest {
    @NotNull(message = "Product Id is compulsory")
    @Min(value = 1, message = "Product Id must be greater than 0")
    private Long productId;
    @NotNull(message = "Product Quantity is required")
    @Positive(message = "Quantity should be greater than 0")
    private double quantity;
}
