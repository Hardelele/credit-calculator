package com.github.hardelele.cca.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidPercentException extends RuntimeException {

    private final String INVALID_PERCENT = "Invalid percent: ";
    private final String BORDER = ", permissible values: 13.9% - 23.9%";
    private HttpStatus httpStatus;
    private String message;

    public InvalidPercentException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = INVALID_PERCENT + message + BORDER;
    }
}
