package dev.saha.customer.dto;

import dev.saha.customer.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateRequest(
        @NotNull(message = "Id is required")
        String id,
        @NotNull(message = "First name is required")
        String firsName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Valid Email is required")
        String email,
        Address address
) {
}
