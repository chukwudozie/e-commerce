package dev.saha.customer.dto;

import dev.saha.customer.model.Address;
import lombok.Builder;

@Builder
public record CustomerResponse(
        String id,
        String firsName,
        String lastName,
        String email,
        Address address
) {
}
