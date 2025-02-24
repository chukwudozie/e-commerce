package dev.saha.productsservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class ProductPurchaseResponse {
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private double quantity;
}
