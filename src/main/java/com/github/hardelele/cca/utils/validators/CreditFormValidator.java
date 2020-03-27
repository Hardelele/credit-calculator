package com.github.hardelele.cca.utils.validators;

import com.github.hardelele.cca.exceptions.InvalidCreditAmountException;
import com.github.hardelele.cca.exceptions.InvalidMonthsCountException;
import com.github.hardelele.cca.models.forms.CreditForm;
import org.springframework.http.HttpStatus;

public class CreditFormValidator {

    public static void validate(CreditForm creditForm) {
        CreditFormValidator.validateAmount(creditForm.getCreditAmount());
        CreditFormValidator.validateMonths(creditForm.getPaymentTerm());
    }

    public static void validateMonths(int months) {
        if (months < 12 || months > 60) {
            throw new InvalidMonthsCountException(Double.toString(months), HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateAmount(double amount) {
        if (amount < 100_000 || amount > 5_000_000) {
            throw new InvalidCreditAmountException(Double.toString(amount), HttpStatus.BAD_REQUEST);
        }
    }
}
