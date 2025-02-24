package dev.saha.customer.exception;

import lombok.*;

//@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
@RequiredArgsConstructor
//@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final String message;
    private final int code;
}
