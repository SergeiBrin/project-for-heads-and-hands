package com.example.projectforheadsandhands.handler;

import com.example.projectforheadsandhands.exception.InvalidParamException;
import com.example.projectforheadsandhands.exception.InvalidRecoveryException;
import com.example.projectforheadsandhands.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        log.error("");
        ApiError apiError = new ApiError(e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ApiError> handleInvalidArgumentException(InvalidParamException e) {
        log.error("");
        ApiError apiError = new ApiError(e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(InvalidRecoveryException.class)
    public ResponseEntity<ApiError> handleInvalidRecoveryException(InvalidRecoveryException e) {
        log.error("");
        ApiError apiError = new ApiError(e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception e) {
        log.error("");
        ApiError apiError = new ApiError(e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
