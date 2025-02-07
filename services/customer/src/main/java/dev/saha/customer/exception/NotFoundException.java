package dev.saha.customer.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper=true)
@Setter
@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{
    private final String message;
}
