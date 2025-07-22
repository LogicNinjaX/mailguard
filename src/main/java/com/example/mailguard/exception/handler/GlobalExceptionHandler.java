package com.example.mailguard.exception.handler;


import com.example.mailguard.dto.response.ErrorResponse;
import com.example.mailguard.exception.CategoryNotFoundException;
import com.example.mailguard.exception.UserNotFoundException;
import com.example.mailguard.exception.UserPreferenceExistException;
import com.example.mailguard.exception.UserPreferenceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class, UserNotFoundException.class, UserPreferenceNotFoundException.class})
    public ResponseEntity<ErrorResponse<String>> entityNotFoundHandler(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(false,"entity not found",  HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(UserPreferenceExistException.class)
    public ResponseEntity<ErrorResponse<String>> preferenceHandler(UserPreferenceExistException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, "already exist", HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> validationExceptionHandler(MethodArgumentNotValidException ex){

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, "invalid request", HttpStatus.BAD_REQUEST.value(), errors));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleGenericException(Exception ex) {
        ErrorResponse<String> error = new ErrorResponse<>(false, "Something went wrong: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
