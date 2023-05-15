package com.sadiker.mobisem.exception;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControl {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(
            MethodArgumentNotValidException manve) {

        Map<String, String> notValids = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            notValids.put(fieldName, message);
        });

        return new ResponseEntity<Object>(notValids, HttpStatus.BAD_REQUEST);

    }

    // genel hata, deneme için
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception exception) {

        return ResponseEntity
                // duruma göre farklı statü verilebilir...
                .status(HttpStatus.SEE_OTHER)
                .body(exception.getMessage());
    }
}
