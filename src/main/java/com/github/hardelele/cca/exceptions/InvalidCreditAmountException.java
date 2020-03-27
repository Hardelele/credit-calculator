package com.github.hardelele.cca.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidCreditAmountException extends RuntimeException {

    private final String INVALID_PERCENT = "Invalid credit amount: ";
    private final String BORDER = ", permissible values: 100 000 - 5 000 000";
    private HttpStatus httpStatus;
    private String message;

    public InvalidCreditAmountException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = INVALID_PERCENT + message + BORDER;
    }
}
