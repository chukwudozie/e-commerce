package dev.saha.customer.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final String message;
    private final int code;
}
