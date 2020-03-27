package com.github.hardelele.cca.utils.builders;

import com.github.hardelele.cca.models.transfers.CreditInfo;
import com.github.hardelele.cca.models.transfers.CreditPayout;
import com.github.hardelele.cca.models.transfers.PayoutInfo;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDateTime;

public class CreditBuilders {

    public static PayoutInfo buildPayoutInfo(CreditInfo creditInfo, double payment, double coef, int counter) {
        double creditRemainderBeforePayment = creditInfo.getCreditRemainder();
        double percentagePart = calculatePercentagePart(creditRemainderBeforePayment, coef);
        double mainPayout = calculateMainPayout(payment, percentagePart);
        creditInfo.setCreditRemainder(creditRemainderBeforePayment - mainPayout);
        return new PayoutInfo(counter, mainPayout, percentagePart);
    }

    public static CreditPayout buildCreditPayout(CreditInfo creditInfo, PayoutInfo payoutInfo) {
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

    private static double calculatePercentagePart(double creditRemainder, double monthCoefficient) {
        return monthCoefficient * creditRemainder;
    }

    private static double calculateMainPayout(double monthlyAnnuityPayment, double percentagePart) {
        return monthlyAnnuityPayment - percentagePart;
    }
}
