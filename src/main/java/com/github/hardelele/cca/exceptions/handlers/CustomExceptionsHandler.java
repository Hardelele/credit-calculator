package com.github.hardelele.cca.exceptions.handlers;

import com.github.hardelele.cca.exceptions.InvalidPercentException;
import com.github.hardelele.cca.transfers.ErrorTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionsHandler {

    @ExceptionHandler(InvalidPercentException.class)
    public ResponseEntity<?> handleInvalidPercentException(InvalidPercentException e) {
        return new ResponseEntity<>(new ErrorTransfer(e.getMessage(), e.getHttpStatus().value()), e.getHttpStatus());
    }
}
