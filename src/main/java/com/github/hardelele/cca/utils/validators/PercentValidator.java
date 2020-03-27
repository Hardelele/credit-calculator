package com.github.hardelele.cca.utils.validators;

import com.github.hardelele.cca.exceptions.InvalidPercentException;
import org.springframework.http.HttpStatus;

public class PercentValidator {
    public static void validate(double percent) {
        if (percent < 12.9 || percent > 23.9) {
            throw new InvalidPercentException(Double.toString(percent), HttpStatus.BAD_REQUEST);
        }
    }
}
