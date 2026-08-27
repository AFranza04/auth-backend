package com.example.auth_backend.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
    String msg = ex.getMessage();
    HttpStatus status;
    if (msg.contains("Invalid credentials")) {
        status = HttpStatus.UNAUTHORIZED;
    } else if (msg.contains("not found")) {
        status = HttpStatus.NOT_FOUND;
    } else {
        status = HttpStatus.CONFLICT;
    }
    return ResponseEntity.status(status).body(Map.of("message", msg));
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }
}