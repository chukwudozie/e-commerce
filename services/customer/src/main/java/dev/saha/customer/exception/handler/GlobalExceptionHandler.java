package dev.saha.customer.exception.handler;


import dev.saha.customer.exception.CustomException;
import dev.saha.customer.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(Map.of("error",e.getMessage(), "timestamp",new Date().toString()), NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,String>> handleCustomException(CustomException e) {
        return new ResponseEntity<>(Map.of("error",e.getMessage(), "timestamp",new Date().toString()), getStatus(e.getCode()));
    }


    private HttpStatus getStatus(int code){
        return switch (code){
            case 400 -> BAD_REQUEST;
            case 401 -> UNAUTHORIZED;
            case 403 -> FORBIDDEN;
            case 404 -> NOT_FOUND;
            case 500 -> INTERNAL_SERVER_ERROR;
            default -> BAD_REQUEST;
        };
    }
}
