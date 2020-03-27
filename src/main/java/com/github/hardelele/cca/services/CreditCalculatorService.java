package com.github.hardelele.cca.services;

import com.github.hardelele.cca.models.forms.CreditForm;
import com.github.hardelele.cca.models.entities.CreditParameters;
import com.github.hardelele.cca.models.transfers.CreditInfo;
import com.github.hardelele.cca.models.transfers.CreditPayout;
import com.github.hardelele.cca.models.transfers.PayoutInfo;
import com.github.hardelele.cca.utils.validators.CreditFormValidator;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCalculatorService {

    private final CreditParametersService creditParametersService;

    private final int YEAR_MONTHS = 12;

    @Autowired
    public CreditCalculatorService(CreditParametersService creditParametersService) {
        this.creditParametersService = creditParametersService;
    }

    public List<CreditPayout> getCreditPayouts(CreditForm creditForm) {
        CreditFormValidator.validate(creditForm);
        CreditInfo creditInfo = buildCreditInfo(creditForm);
        return calculateCreditPayoutsList(creditInfo);
    }

    private List<CreditPayout> calculateCreditPayoutsList(CreditInfo creditInfo) {

        double monthlyCoefficient = creditInfo.getMonthCoefficient();
        double annuityPayment = creditInfo.getAnnuityPayment();
        int paymentTerm = creditInfo.getPaymentTerm();

        List<CreditPayout> creditPayoutList = new ArrayList<>();
        for (int counter = 0; counter < paymentTerm; counter++) {
            double creditRemainderBeforePayment = creditInfo.getCreditRemainder();
            double percentagePart = calculatePercentagePart(creditRemainderBeforePayment, monthlyCoefficient);
            double mainPayout = calculateMainPayout(annuityPayment, percentagePart);
            creditInfo.setCreditRemainder(creditRemainderBeforePayment - mainPayout);
            PayoutInfo payoutInfo = new PayoutInfo(counter, mainPayout, percentagePart);
            creditPayoutList.add(buildCreditPayout(creditInfo, payoutInfo));
        }
        return creditPayoutList;
    }

    private CreditPayout buildCreditPayout(CreditInfo creditInfo, PayoutInfo payoutInfo) {
        int counter = payoutInfo.getCounter();
        long   paymentDateTimestamp = LocalDateTime.now().plusMonths(counter).getNano();
        double annuityPayment = Precision.round(creditInfo.getAnnuityPayment(),2);
        double percentagePart = Precision.round(payoutInfo.getPercentagePart(),2);
        double mainPayout     = Precision.round(payoutInfo.getMainPayout(),2);
        double debt           = Precision.round(creditInfo.getCreditRemainder(),2);
        return CreditPayout.builder()
                .number(counter + 1)
                .fullPayout(annuityPayment)
                .percentPayout(percentagePart)
                .mainPayout(mainPayout)
                .timestamp(paymentDateTimestamp)
                .debt(debt)
                .build();
    }

    private CreditInfo buildCreditInfo(CreditForm creditForm) {
        int paymentTerm = creditForm.getPaymentTerm();
        double creditAmount     = creditForm.getCreditAmount();
        double monthCoefficient = extractPercent() / (YEAR_MONTHS * 100);
        double annuityPayment   = calculateMonthlyAnnuityPayment(paymentTerm, creditAmount, monthCoefficient);
        double creditRemainder  = creditForm.getCreditAmount();
        return CreditInfo.builder()
                .monthCoefficient(monthCoefficient)
                .annuityPayment(annuityPayment)
                .paymentTerm(paymentTerm)
                .creditRemainder(creditRemainder)
                .build();
    }

    private double extractPercent() {
        List<CreditParameters> creditParametersList = creditParametersService.getCreditParameters();
        CreditParameters creditParameters = creditParametersList.get(0);
        return creditParameters.getPercent();
    }

    private double calculateMonthlyAnnuityPayment(int paymentTerm, double creditAmount, double monthCoefficient) {
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
