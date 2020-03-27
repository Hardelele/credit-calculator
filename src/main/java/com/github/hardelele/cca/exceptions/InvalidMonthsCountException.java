package com.github.hardelele.cca.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidMonthsCountException extends RuntimeException {

    private final String INVALID_PERCENT = "Invalid months count: ";
    private final String BORDER = ", permissible values: 12-60 months";
    private HttpStatus httpStatus;
    private String message;

    public InvalidMonthsCountException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = INVALID_PERCENT + message + BORDER;
    }
}
