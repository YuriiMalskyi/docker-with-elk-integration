package com.malskyi.kafka.exception.handler;

import com.malskyi.kafka.exception.AuthorNotFoundException;
import com.malskyi.kafka.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // додаємо обробник для помилки BookNotFoundException
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

//    // додаємо обробник для будь якої неочікуваної помилки
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionResponse> handleUnexpectedException(Exception ex) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse();
//        exceptionResponse.setMessage("Unknown error has occurred. Please contact support.");
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
//    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExceptionResponse {
        private String message;
    }
}
