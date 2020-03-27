package com.github.hardelele.cca.services;

import com.github.hardelele.cca.forms.CreditForm;
import com.github.hardelele.cca.models.CreditParameters;
import com.github.hardelele.cca.transfers.CreditPayout;
import org.apache.commons.math3.util.Precision;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCalculatorService {

    private final Mapper mapper;

    private final CreditParametersService creditParametersService;

    private final int YEAR_MONTHS = 12;

    @Autowired
    public CreditCalculatorService(Mapper mapper, CreditParametersService creditParametersService) {
        this.mapper = mapper;
        this.creditParametersService = creditParametersService;
    }

    public List<CreditPayout> getCreditPayouts(CreditForm creditForm) {
        double percent = extractPercent();
        double yearCoefficient = percent / 100;
        double monthCoefficient = yearCoefficient / YEAR_MONTHS;
        double annuityPayment = calculateMonthlyAnnuityPayment(creditForm, monthCoefficient);
        int paymentTerm = creditForm.getPaymentTerm();
        double creditRemainder = creditForm.getCreditAmount();
        return calculateCreditPayoutsList(paymentTerm, annuityPayment, creditRemainder, monthCoefficient);
    }

    private List<CreditPayout> calculateCreditPayoutsList(int paymentTerm, double annuityPayment, double creditRemainder, double monthCoefficient) {
        List<CreditPayout> creditPayoutList = new ArrayList<>();
        for (int counter = 0; counter < paymentTerm; counter++) {
            CreditPayout creditPayout = new CreditPayout();
            creditPayout.setNumber(counter + 1);
            creditPayout.setFullPayout(Precision.round(annuityPayment,2));
            double percentagePart = calculatePercentagePart(creditRemainder, monthCoefficient);
            creditPayout.setPercentPayout(Precision.round(percentagePart,2));
            double mainPayout = calculateMainPayout(annuityPayment, percentagePart);
            creditPayout.setMainPayout(Precision.round(mainPayout, 2));
            long paymentDate = LocalDateTime.now().plusMonths(counter).getNano();
            creditPayout.setTimestamp(paymentDate);
            creditRemainder = creditRemainder - mainPayout;
            creditPayout.setDebt(Precision.round(creditRemainder, 2));
            creditPayoutList.add(creditPayout);
        }
        return creditPayoutList;
    }

    private double extractPercent() {
        List<CreditParameters> creditParametersList = creditParametersService.getCreditParameters();
        CreditParameters creditParameters = creditParametersList.get(0);
        return creditParameters.getPercent();
    }

    private double calculateMonthlyAnnuityPayment(CreditForm creditForm, double monthCoefficient) {
        double creditAmount = creditForm.getCreditAmount();
        int paymentTerm = creditForm.getPaymentTerm();
        double annuityRatio = monthCoefficient + (monthCoefficient / (Math.pow(1 + monthCoefficient, paymentTerm) - 1));
        return creditAmount * annuityRatio;
    }

    private double calculatePercentagePart(double creditRemainder, double monthCoefficient) {
        return monthCoefficient * creditRemainder;
    }

    private double calculateMainPayout(double monthlyAnnuityPayment, double percentagePart) {
        return monthlyAnnuityPayment - percentagePart;
    }
}
